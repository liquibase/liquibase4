package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.Index
import liquibase.item.core.IndexReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropIndexesActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                indexName_asTable    : action.indexes*.name,
                indexRelation_asTable: action.indexes*.relation,
                indexSchema_asTable  : action.indexes*.indexSchema,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        }
    }


    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(DropIndexesAction, [
                indexes: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(IndexReference, [
                        name       : getItemNames(Index, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        container  : getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        indexSchema: connectionSupplier.getAllSchemas()
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        DropIndexesAction dropAction = (DropIndexesAction) action
        def colName = standardCaseItemName("test_col", Column, scope)

        def snapshot = new Snapshot(scope)

        for (def index : dropAction.indexes) {
            def table = new Table((index.relation == null ? standardCaseItemName("test_table", Table, scope) : index.relation.name), (index.relation == null ? null : index.relation.schema))
            snapshot.add(table)
            snapshot.add(new Column(colName, table.toReference(), new DataType(DataType.StandardType.INTEGER), true))

            def indexObj = new Index(index.name, table.toReference(), new Index.IndexedColumn(colName))
            indexObj.indexSchema = index.indexSchema
            snapshot.add(indexObj)
        }

        return snapshot
    }
}

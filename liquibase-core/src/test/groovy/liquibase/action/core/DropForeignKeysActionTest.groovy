package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.ForeignKey
import liquibase.item.core.ForeignKeyReference
import liquibase.item.core.Index
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropForeignKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                name_asTable    : action.foreignKeys*.name,
                relation_asTable: action.foreignKeys*.relation], action, conn, scope)

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
        return TestUtil.createAllPermutations(DropForeignKeysAction, [
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(ForeignKeyReference, [
                        name     : getItemNames(ForeignKey, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        container: getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def dropAction = (DropForeignKeysAction) action

        def snapshot = new Snapshot(scope)

        for (def fk : dropAction.foreignKeys) {
            snapshot.add(new Table(fk.relation.name, fk.relation.schema))

            def baseColName = standardCaseItemName("base_col", Column, scope)
            snapshot.add(new Column(baseColName, fk.relation, new DataType(DataType.StandardType.INTEGER), true));

            def refTable = new Table(standardCaseItemName("ref_table", Table, scope), fk.relation.schema)
            def refColumnName = standardCaseItemName("ref_col", Column, scope)

            snapshot.add(refTable)
            snapshot.add(new Column(standardCaseItemName("ref_col", Column, scope), refTable.toReference(), new DataType(DataType.StandardType.INTEGER), true));
            snapshot.add(new Index(standardCaseItemName("base_idx", Index, scope), refTable.toReference(), new Index.IndexedColumn(refColumnName)));

            snapshot.add(new ForeignKey(fk.name, fk.relation, refTable.toReference(), [baseColName], [refColumnName]));
        }
        return snapshot
    }
}


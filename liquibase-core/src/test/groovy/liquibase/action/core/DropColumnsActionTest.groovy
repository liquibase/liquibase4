package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

class DropColumnsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                column_asTable: action.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Column, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(DropColumnsAction, [
                columns: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(ColumnReference, [
                        name     : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        container: getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def dropAction = (DropColumnsAction) action

        def snapshot = new Snapshot(scope)
        def seenTables = new HashSet<RelationReference>()
        for (def column : dropAction.columns) {
            snapshot.add(new Column(column.name, column.relation, new DataType(DataType.StandardType.INTEGER), true))
            if (seenTables.add(column.relation)) {
                snapshot.add(new Table(column.relation.name, column.relation.schema))
                snapshot.add(new Column(standardCaseItemName("other_col", Column, scope), column.relation, new DataType(DataType.StandardType.INTEGER), true))
            }
        }

        return snapshot
    }

}

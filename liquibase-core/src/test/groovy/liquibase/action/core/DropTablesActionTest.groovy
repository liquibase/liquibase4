package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropTablesActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                table_asTable  : action.tables,
                cascade_asTable: action.cascadeConstraints
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
        return CollectionUtil.addTo(
                TestUtil.createAllPermutations(DropTablesAction, [
                        tables            : CollectionUtil.toSingletonLists(getItemReferences(Table, connectionSupplier.getAllSchemas(), scope)),
                        cascadeConstraints: [true, false]
                ]),
                new DropTablesAction(new RelationReference(Table, null, null))) //add null-name action
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        for (def table : action.tables) {
            snapshot.add(new Table(table.name, table.schema))

            snapshot.add(new Column(standardCaseItemName("test_col", Column, scope), table, new DataType(DataType.StandardType.INTEGER), true))
        }

        return snapshot
    }
}

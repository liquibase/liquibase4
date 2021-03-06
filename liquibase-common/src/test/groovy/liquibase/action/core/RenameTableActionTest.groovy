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

class RenameTableActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can rename from createAllActionPermutations"() {
        testAction([
                oldName_asTable: action.oldName,
                newName_asTable: action.newName,
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
        return TestUtil.createAllPermutations(RenameTableAction, [
                oldName: getItemReferences(Table, connectionSupplier.getAllSchemas(), scope),
                newName: getItemReferences(Table, connectionSupplier.getAllSchemas(), scope),
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        RelationReference oldName = action.oldName

        def table = new Table(oldName.name, oldName.schema)
        snapshot.add(table)
        snapshot.add(new Column(standardCaseItemName("id", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), true))

        return snapshot;
    }
}

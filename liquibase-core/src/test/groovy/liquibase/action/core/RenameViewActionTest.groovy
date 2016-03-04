package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.DatabaseObjectReference
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class RenameViewActionTest extends AbstractActionTest {

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
        return TestUtil.createAllPermutations(RenameViewAction, [
                oldName: getItemReferences(View, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                newName: getItemReferences(View, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        RelationReference oldName = action.oldName

        SchemaReference schema = oldName.schema
        def table = new Table(standardCaseItemName("test_table", Table, scope), schema)
        snapshot.add(table)
        snapshot.add(new Column(standardCaseItemName("id", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new View(oldName.name, oldName.schema, new StringClauses().append("select * from " + table.toReference())))

        return snapshot;
    }
}

package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class AlterTableActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can alter tables"() {
        expect:
        testAction([
                table_asTable        : action.table,
                newDefinition_asTable: action.newDefinition
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
        return TestUtil.createAllPermutations(AlterTableAction, [
                table        : getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                newDefinition: [new StringClauses().append("add").append("id int")]
        ])

    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def tableRef = ((AlterTableAction) action).table

        Snapshot snapshot = new Snapshot(scope);

        snapshot.add(new Table(tableRef.name, tableRef.schema))
        snapshot.add(new Column(standardCaseItemName("col1", Column, scope), tableRef, DataType.parse("int"), true))
        snapshot.add(new Column(standardCaseItemName("col2", Column, scope), tableRef, DataType.parse("int"), true))

        return snapshot
    }
}

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
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class AlterColumnActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can alter columns"() {
        expect:
        testAction([
                column_asTable       : action.column,
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
        return TestUtil.createAllPermutations(AlterColumnAction, [
                column       : TestUtil.createAllPermutations(ColumnReference, [
                        name     : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        container: getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                ]),
                newDefinition: [new StringClauses().append("int")]
        ])

    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def columnRef = ((AlterColumnAction) action).column

        Snapshot snapshot = new Snapshot(scope);

        snapshot.add(new Table(columnRef.relation.name, columnRef.relation.schema))
        snapshot.add(new Column(columnRef.name, columnRef.relation, DataType.parse("int"), true))
        snapshot.add(new Column(standardCaseItemName("other_col", Column, scope), columnRef.relation, DataType.parse("int"), true))

        return snapshot
    }
}

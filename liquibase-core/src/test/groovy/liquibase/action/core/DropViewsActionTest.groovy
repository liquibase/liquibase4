package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.core.RelationReference
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropViewsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                view_asTable: action.views,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(DropViewsAction, [
                views: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(RelationReference, [
                        name     : getItemNames(View, scope),
                        container: connectionSupplier.getAllSchemas()
                ]).each {
                    it.type = View
                })
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def dropAction = (DropViewsAction) action

        def snapshot = new Snapshot(scope)
        for (def view : dropAction.views) {
            def table = new Table(standardCaseItemName("test_table", Table, scope), view.schema)
            snapshot.add(table)
            snapshot.add(new Column(standardCaseItemName("id", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), true))
            snapshot.add(new View(view.name, view.schema, new StringClauses().append("select * from "+table.toReference())))
        }

        return snapshot
    }

}

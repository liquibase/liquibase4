package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class DeleteDataActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can delete from createAllActionPermutations"() {
        expect:
        testAction([
                relation_asTable: action.relation,
                where_asTable   : action.where,
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

        return TestUtil.createAllPermutations(DeleteDataAction, [
                relation: getItemReferences(Table, connectionSupplier.allSchemas, scope),
                where   : [
                        new StringClauses().append("1=1")
                ]

        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        snapshot.add(new Table(action.relation.name, action.relation.schema))
        snapshot.add(new Column(standardCaseItemName("test_col", Column, scope), action.relation, new DataType(DataType.StandardType.INTEGER), true))
        return snapshot
    }

}

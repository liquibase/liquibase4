package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.PrimaryKey
import liquibase.item.core.PrimaryKeyReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropPrimaryKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                name_asTable    : action.primaryKeys*.name,
                relation_asTable: action.primaryKeys*.relation,
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
        return TestUtil.createAllPermutations(DropPrimaryKeysAction, [
                primaryKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(PrimaryKeyReference, [
                        name       : getItemNames(PrimaryKey, scope),
                        container  : getItemReferences(Table, connectionSupplier.getAllSchemas(), scope),
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        DropPrimaryKeysAction dropAction = (DropPrimaryKeysAction) action
        def colName = standardCaseItemName("test_col", Column, scope)

        def snapshot = new Snapshot(scope)

        for (def pk : dropAction.primaryKeys) {
            def table = new Table((pk.relation == null ? standardCaseItemName("test_table", Table, scope) : pk.relation.name), (pk.relation == null ? null : pk.relation.schema))
            snapshot.add(table)
            snapshot.add(new Column(colName, table.toReference(), new DataType(DataType.StandardType.INTEGER), false))

            def pkObject = new PrimaryKey(pk.name, table.toReference(), colName)
            snapshot.add(pkObject)
        }

        return snapshot
    }

}

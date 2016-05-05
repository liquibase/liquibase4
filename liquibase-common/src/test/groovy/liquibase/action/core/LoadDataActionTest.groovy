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
import liquibase.resource.MockResourceAccessor
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class LoadDataActionTest extends AbstractActionTest {

    /**
     * only test that actually executes SQL. The rest of the testing is done in {@link liquibase.actionlogic.core.LoadDataLogicTest}
     */
    @Unroll("#featureName: #action on #conn")
    def "can load simple csv"() {
        expect:
        testAction([
                table_asTable: action.table,
                path_asTable : action.path,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = setupResourceAccessor(JUnitScope.getInstance(it))
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(LoadDataAction, [
                            table  : it.allSchemas.collect {
                                return new RelationReference(Table, standardCaseItemName("test_table", Table, scope), it)
                            },
                            path   : ["com/example/simple.csv"],
                            columns: [[
                                              new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR)),
                                              new LoadDataAction.LoadDataColumn("username", new DataType(DataType.StandardType.VARCHAR)),
                                              new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER)),
                                      ]]
                    ])], new ValidActionFilter(scope))
        }
    }

    Scope setupResourceAccessor(JUnitScope scope) {
        def resourceAccessor = new MockResourceAccessor();
        resourceAccessor.addData("com/example/simple.csv", """
name, username, age
Bob Johnson, bjohnson, 42
John Doe, jdoe, 23
""".trim())

        return scope.child(Scope.Attr.resourceAccessor, resourceAccessor)
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(LoadDataAction, [
                table                : connectionSupplier.allSchemas.collect {
                    return new RelationReference(Table, standardCaseItemName("test_table", Table, scope), it)
                },
                path                 : ["com/example/simple.csv"],
                commentLineStart     : [(char) '-', (char) '#'],
                encoding             : ["UTF-8", "ASCII"],
                columnsForUpdateCheck: [[standardCaseItemName("name", Column, scope)]]
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        LoadDataAction loadAction = (LoadDataAction) action

        def snapshot = new Snapshot(scope)
        snapshot.add(new Table(loadAction.table.name, loadAction.table.schema))
        snapshot.add(new Column(standardCaseItemName("name", Column, scope), loadAction.table, new DataType(DataType.StandardType.VARCHAR, 100), true))
        snapshot.add(new Column(standardCaseItemName("username", Column, scope), loadAction.table, new DataType(DataType.StandardType.VARCHAR, 100), true))
        snapshot.add(new Column(standardCaseItemName("age", Column, scope), loadAction.table, new DataType(DataType.StandardType.INTEGER), true))

        return snapshot
    }

}

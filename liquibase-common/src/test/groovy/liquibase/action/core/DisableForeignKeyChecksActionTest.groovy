package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.action.ExecuteSqlAction
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.exception.ActionPerformException
import liquibase.exception.UnexpectedLiquibaseException
import liquibase.item.core.Column
import liquibase.item.core.ForeignKey
import liquibase.item.core.Index
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import spock.lang.Unroll

class DisableForeignKeyChecksActionTest extends AbstractActionTest {

    @Unroll("#featureName: on #conn")
    def "foreign keys are disabled"() {
        testAction([:], action, conn, scope, {
            plan, result ->
                StringClauses sql = new StringClauses()
                        .append("insert into")
                        .append(standardCaseItemName("table1", Table, scope))
                        .append("(" + standardCaseItemName("col1", Column, scope) + ")")
                        .append("values (1)");

                try {
                    ((Scope) scope).getSingleton(ActionExecutor).execute(new ExecuteSqlAction(sql), scope)
                } catch (ActionPerformException e) {
                    throw new UnexpectedLiquibaseException("Should not have gotten an exception inserting into a disable foreign key", e);
                }
        })

        where:
        [conn, scope, action] << ignoreIfEmpty("cannot disable foreign keys", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    [new DisableForeignKeyChecksAction()]
            ], new ValidActionFilter(scope))
        })
    }

    @Override
    protected void cleanupDatabase(Snapshot snapshot, Scope scope) {
        super.cleanupDatabase(snapshot, scope);
        scope.getSingleton(ActionExecutor).execute(new EnableForeignKeyChecksAction(), scope)
    }

    @Override
    def List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return [new DisableForeignKeyChecksAction()]
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        def table1 = new Table(standardCaseItemName("table1", Table, scope))
        def table2 = new Table(standardCaseItemName("table2", Table, scope))

        def table1Col1 = new Column(standardCaseItemName("col1", Column, scope), table1.toReference(), DataType.parse("int"), true)
        def table1Col2 = new Column(standardCaseItemName("col2", Column, scope), table1.toReference(), DataType.parse("int"), true)
        def table2Col1 = new Column(standardCaseItemName("col1", Column, scope), table2.toReference(), DataType.parse("int"), true)
        def table2Col2 = new Column(standardCaseItemName("col2", Column, scope), table2.toReference(), DataType.parse("int"), true)

        snapshot.add(table1)
        snapshot.add(table2)


        snapshot.add(table1Col1)
        snapshot.add(table1Col2)
        snapshot.add(table2Col1)
        snapshot.add(table2Col2)

        snapshot.add(new Index(standardCaseItemName("idx_table2", Index, scope), table2.toReference(), new Index.IndexedColumn(table2Col1.name)))
        snapshot.add(new ForeignKey(null, table1.toReference(), table2.toReference(), [table1Col1.name], [table2Col1.name]))

        snapshot.add(new Index(standardCaseItemName("idx_table1", Index, scope), table1.toReference(), new Index.IndexedColumn(table1Col2.name)))
        snapshot.add(new ForeignKey(null, table2.toReference(), table1.toReference(), [table2Col2.name], [table1Col2.name]))
        return snapshot
    }
}

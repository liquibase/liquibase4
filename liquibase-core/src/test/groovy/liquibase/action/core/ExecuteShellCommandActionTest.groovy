package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class ExecuteShellCommandActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action")
    def "can run from createAllActionPermutations"() {
        expect:
        testAction([
                executable_asTable: action.executable,
                args_asTable      : action.args,
                osFilters_asTable : action.osFilters,
        ], action, null, scope, {
            plan, result ->
                assert result.message.length() > 0
        })


        where:
        [scope, action] <<
                CollectionUtil.permutationsWithoutNulls([
                        [JUnitScope.instance],
                        createAllActionPermutations(null, JUnitScope.instance)
                ], new ValidActionFilter(JUnitScope.instance))
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutationsWithoutNulls(ExecuteShellCommandAction, [
                executable: [null, "tree.com", "\\windows\\system32\\tree.com"],
                args      : [null, ["/F"], ["/F", "/A"]],
                osFilters : [["windows"]]
        ]).plus(TestUtil.createAllPermutationsWithoutNulls(ExecuteShellCommandAction, [
                executable: [null, "echo"],
                args      : [null, ["hello world"]],
                osFilters : [["linux"], ["mac"], ["linux", "mac"]]
        ]));
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        return new Snapshot(scope) //nothing to set up
    }
}
package liquibase.action.core;

import liquibase.JUnitScope;
import liquibase.Scope;
import liquibase.action.AbstractActionTest;
import liquibase.action.Action;
import liquibase.database.ConnectionSupplier;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.item.core.Table;
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot;
import liquibase.util.CollectionUtil;
import liquibase.util.TestUtil;
import spock.lang.Unroll;

import java.util.List;

public class RollbackActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action.describe() on #conn")
    def "Valid parameter permutations work"() {
        expect:
        testAction([:], action, conn, scope, { plan, result ->
            //nothing to check
        })

        where:
        [conn, scope, action] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
            [it],
            [scope],
            createAllActionPermutations(it, scope),
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return [new RollbackAction()]
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        return null
    }
}

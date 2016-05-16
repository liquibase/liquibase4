package liquibase.action.core

import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot
import spock.lang.Specification

class CommentActionTest extends AbstractActionTest {

    def "nothing to test"() {
        expect:
        assert true
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        return null
    }
}

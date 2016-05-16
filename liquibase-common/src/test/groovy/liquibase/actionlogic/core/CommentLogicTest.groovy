package liquibase.actionlogic.core

import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.database.ConnectionSupplier
import spock.lang.Specification

class CommentLogicTest extends AbstractActionLogicTest {

    @Override
    protected String collectAllValidationErrors(AbstractActionTest actionTest, ConnectionSupplier supplier, Scope scope) {
        return null;
    }

    @Override
    protected String getExpectedValidationErrors() {
        return null
    }

    def "nothing to test"() {
        expect:
        assert true
    }
}

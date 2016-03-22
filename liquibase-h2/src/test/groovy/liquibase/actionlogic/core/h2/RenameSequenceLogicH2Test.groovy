package liquibase.actionlogic.core.h2

import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.database.ConnectionSupplier

class RenameSequenceLogicH2Test extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return """
No valid objects create:
        RenameSequenceAction: action is not supported
""".trim()
    }

    @Override
    protected String collectAllValidationErrors(AbstractActionTest actionTest, ConnectionSupplier supplier, Scope scope) {
        try {
            return super.collectAllValidationErrors(actionTest, supplier, scope)
        } catch (AssertionError e) {
            return e.getMessage()
        }
    }
}

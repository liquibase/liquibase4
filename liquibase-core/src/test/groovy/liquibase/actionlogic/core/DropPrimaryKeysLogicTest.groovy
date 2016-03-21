package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropPrimaryKeysLogicTest extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return """
DropPrimaryKeysAction.primaryKeys is required
DropPrimaryKeysAction.primaryKeys.container is required
""".trim()
    }
}

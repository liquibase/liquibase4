package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropAllForeignKeysLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
DropAllForeignKeysAction.table is required
""".trim()
    }
}

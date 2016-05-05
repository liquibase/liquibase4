package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class UpdateDataLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
UpdateDataAction.columns is required
UpdateDataAction.columns.name is required
UpdateDataAction.relation is required
""".trim()
    }
}


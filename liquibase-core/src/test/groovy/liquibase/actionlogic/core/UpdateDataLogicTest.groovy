package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class UpdateDataLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
UpdateDataAction.columns is required
UpdateDataAction.columns.name is required
UpdateDataAction.relation is required
""".trim()
    }
}


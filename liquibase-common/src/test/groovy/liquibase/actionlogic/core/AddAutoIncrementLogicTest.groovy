package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AddAutoIncrementLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
""".trim()
    }

}

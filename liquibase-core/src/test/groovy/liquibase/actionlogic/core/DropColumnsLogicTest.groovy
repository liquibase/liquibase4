package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropColumnsLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropColumnsAction.columns is required
DropColumnsAction.columns.container is required
DropColumnsAction.columns.name is required
""".trim()
    }
}

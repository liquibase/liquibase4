package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropColumnsLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropColumnsAction.columns is required
DropColumnsAction.columns.container is required
DropColumnsAction.columns.name is required
""".trim()
    }
}

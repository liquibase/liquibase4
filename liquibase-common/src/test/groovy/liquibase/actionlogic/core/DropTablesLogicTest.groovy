package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropTablesLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropTablesAction.tables is required
DropTablesAction.tables.name is required
""".trim()
    }
}

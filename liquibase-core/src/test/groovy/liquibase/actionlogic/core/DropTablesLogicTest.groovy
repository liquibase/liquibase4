package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropTablesLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropTablesAction.tables is required
DropTablesAction.tables.name is required
""".trim()
    }
}

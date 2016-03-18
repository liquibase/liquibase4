package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AlterTableLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterTableAction.newDefinition is required
AlterTableAction.table is required
""".trim()
    }
}

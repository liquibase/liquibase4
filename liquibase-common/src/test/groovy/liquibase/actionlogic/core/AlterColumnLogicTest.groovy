package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AlterColumnLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterColumnAction.column is required
AlterColumnAction.column.container is required
AlterColumnAction.column.name is required
AlterColumnAction.newDefinition is required
""".trim()
    }
}

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AlterDefaultValueLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
AlterDefaultValueAction.column is required
AlterDefaultValueAction.column.container is required
AlterDefaultValueAction.column.name is required
""".trim()
    }
}


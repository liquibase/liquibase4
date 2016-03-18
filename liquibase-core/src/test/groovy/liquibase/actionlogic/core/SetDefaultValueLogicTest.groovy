package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class SetDefaultValueLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
SetDefaultValueAction.column is required
SetDefaultValueAction.column.container is required
SetDefaultValueAction.column.name is required
""".trim()
    }
}


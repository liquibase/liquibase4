package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class SetDefaultValueLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
SetDefaultValueAction.column is required
SetDefaultValueAction.column.container is required
SetDefaultValueAction.column.name is required
""".trim()
    }
}


package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropDefaultValueLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropDefaultValueAction.column is required
DropDefaultValueAction.column.container is required
DropDefaultValueAction.column.name is required
""".trim()
    }
}


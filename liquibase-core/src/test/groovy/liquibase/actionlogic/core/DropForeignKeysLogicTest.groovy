package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropForeignKeysLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropForeignKeysAction.foreignKeys is required
DropForeignKeysAction.foreignKeys.container is required
DropForeignKeysAction.foreignKeys.name is required
""".trim()
    }
}


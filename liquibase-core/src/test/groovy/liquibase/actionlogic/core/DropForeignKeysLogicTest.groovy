package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropForeignKeysLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropForeignKeysAction.foreignKeys is required
DropForeignKeysAction.foreignKeys.container is required
DropForeignKeysAction.foreignKeys.name is required
""".trim()
    }
}


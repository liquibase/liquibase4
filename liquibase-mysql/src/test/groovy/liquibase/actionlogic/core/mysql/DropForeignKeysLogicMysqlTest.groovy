package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class DropForeignKeysLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
DropForeignKeysAction.foreignKeys is required
DropForeignKeysAction.foreignKeys.container is required
DropForeignKeysAction.foreignKeys.name is required
""".trim()
    }
}

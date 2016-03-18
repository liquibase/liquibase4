package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class DropForeignKeysLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
DropForeignKeysAction.foreignKeys is required
DropForeignKeysAction.foreignKeys.container is required
DropForeignKeysAction.foreignKeys.name is required
""".trim()
    }
}

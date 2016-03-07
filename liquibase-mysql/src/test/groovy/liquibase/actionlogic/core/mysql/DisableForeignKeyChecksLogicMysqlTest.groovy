package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class DisableForeignKeyChecksLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
""".trim()
    }
}

package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class EnableForeignKeyChecksLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
""".trim()
    }
}

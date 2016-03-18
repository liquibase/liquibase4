package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class EnableForeignKeyChecksLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
""".trim()
    }
}

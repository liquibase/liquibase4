package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class DisableForeignKeyChecksLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
""".trim()
    }
}

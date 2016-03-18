package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class AlterRemarksLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterRemarksAction.object is required
AlterRemarksAction.object.type: only supports tables
""".trim()
    }
}

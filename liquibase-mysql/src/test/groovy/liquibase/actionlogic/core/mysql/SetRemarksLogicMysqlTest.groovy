package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class SetRemarksLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
SetRemarksAction.object is required
SetRemarksAction.object.type: only supports tables
""".trim()
    }
}

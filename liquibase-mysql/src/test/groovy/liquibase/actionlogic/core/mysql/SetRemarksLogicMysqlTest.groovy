package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class SetRemarksLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
SetRemarksAction.object is required
SetRemarksAction.object.type: only supports tables
""".trim()
    }
}

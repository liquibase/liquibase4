package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class RenameViewLogicMysqlTest
        extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameViewAction.newName is required
RenameViewAction.newName: cannot rename to a different Schema
RenameViewAction.newName: cannot rename to the same name
RenameViewAction.oldName is required
""".trim()
    }
}


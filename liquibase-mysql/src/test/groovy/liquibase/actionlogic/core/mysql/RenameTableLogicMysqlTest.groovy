package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class RenameTableLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameTableAction.newName is required
RenameTableAction.newName: cannot rename to a different Schema
RenameTableAction.newName: cannot rename to the same name
RenameTableAction.oldName is required
""".trim()
    }
}

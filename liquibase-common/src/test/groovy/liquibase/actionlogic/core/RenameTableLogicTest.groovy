package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class RenameTableLogicTest extends AbstractActionLogicTest {
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

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class RenameViewLogicTest extends AbstractActionLogicTest {
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

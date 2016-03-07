package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class RenameColumnLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
RenameColumnAction.newName is required
RenameColumnAction.newName: cannot rename to the same name
RenameColumnAction.oldName is required
RenameColumnAction.relation is required
""".trim()
    }
}

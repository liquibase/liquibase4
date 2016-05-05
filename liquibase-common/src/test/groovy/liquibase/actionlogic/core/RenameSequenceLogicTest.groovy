package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class RenameSequenceLogicTest extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return """
RenameSequenceAction.newName is required
RenameSequenceAction.oldName is required
""".trim()
    }
}

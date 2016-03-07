package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractLogicTest

class RenameViewLogicH2Test extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameViewAction.newName is required
RenameViewAction.newName: cannot rename to a different Schema
RenameViewAction.newName: cannot rename to the same name
RenameViewAction.oldName is required
""".trim();
    }
}

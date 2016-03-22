package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest

class RenameTableLogicH2Test extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameTableAction.newName is required
RenameTableAction.newName: cannot rename to a different Schema
RenameTableAction.newName: cannot rename to the same name
RenameTableAction.oldName is required
""".trim();
    }
}

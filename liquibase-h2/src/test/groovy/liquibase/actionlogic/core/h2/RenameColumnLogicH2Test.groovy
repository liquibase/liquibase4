package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractLogicTest

class RenameColumnLogicH2Test extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameColumnAction.newName is required
RenameColumnAction.newName: cannot rename to the same name
RenameColumnAction.oldName is required
RenameColumnAction.relation is required
""".trim();
    }
}

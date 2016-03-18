package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class RenameColumnLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
RenameColumnAction.columnDefinition is required
RenameColumnAction.newName is required
RenameColumnAction.newName: cannot rename to the same name
RenameColumnAction.oldName is required
RenameColumnAction.relation is required
""".trim()
    }
}

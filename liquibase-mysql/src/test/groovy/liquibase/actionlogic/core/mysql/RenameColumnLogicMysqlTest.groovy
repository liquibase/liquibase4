package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class RenameColumnLogicMysqlTest extends AbstractLogicTest {

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

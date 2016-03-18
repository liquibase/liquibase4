package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class AlterDefaultValueLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterDefaultValueAction.column is required
AlterDefaultValueAction.column.container is required
AlterDefaultValueAction.column.name is required
""".trim()
    }
}

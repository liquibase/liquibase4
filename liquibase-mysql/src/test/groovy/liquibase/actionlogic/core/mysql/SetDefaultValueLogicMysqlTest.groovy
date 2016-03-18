package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class SetDefaultValueLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
SetDefaultValueAction.column is required
SetDefaultValueAction.column.container is required
SetDefaultValueAction.column.name is required
""".trim()
    }
}

package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class DropDefaultValueLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
DropDefaultValueAction.column is required
DropDefaultValueAction.column.container is required
DropDefaultValueAction.column.name is required
""".trim()
    }
}

package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class AddAutoIncrementLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddAutoIncrementAction.autoIncrementInformation.incrementBy is not supported
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
AddAutoIncrementAction.dataType is required
""".trim()
    }
}

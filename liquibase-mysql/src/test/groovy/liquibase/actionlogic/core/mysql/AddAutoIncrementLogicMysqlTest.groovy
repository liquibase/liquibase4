package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest
import spock.lang.Specification

class AddAutoIncrementLogicMysqlTest extends AbstractLogicTest {

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

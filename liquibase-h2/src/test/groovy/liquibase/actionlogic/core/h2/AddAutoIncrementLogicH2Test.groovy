package liquibase.actionlogic.core.h2

import liquibase.action.core.AddAutoIncrementActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.actionlogic.core.AddAutoIncrementLogicTest
import liquibase.database.core.h2.H2ConnectionSupplier

class AddAutoIncrementLogicH2Test extends AbstractLogicTest {

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

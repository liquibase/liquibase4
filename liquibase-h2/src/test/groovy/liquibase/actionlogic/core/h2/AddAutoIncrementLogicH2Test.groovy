package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest

class AddAutoIncrementLogicH2Test extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddAutoIncrementAction.autoIncrementDetails.incrementBy is not supported
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
AddAutoIncrementAction.dataType is required
""".trim()
    }
}

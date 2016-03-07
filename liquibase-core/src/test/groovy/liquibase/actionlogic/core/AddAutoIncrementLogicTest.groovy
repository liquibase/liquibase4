package liquibase.actionlogic.core

import liquibase.action.core.AddAutoIncrementActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.GenericConnectionSupplier

class AddAutoIncrementLogicTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
""".trim()
    }

}

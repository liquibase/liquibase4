package liquibase.actionlogic.core

import liquibase.action.core.AddAutoIncrementActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.GenericDatabaseSupplier

class AddAutoIncrementLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddAutoIncrementActionTest(), new GenericDatabaseSupplier()) == """
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
""".trim()
    }
}

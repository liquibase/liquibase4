package liquibase.actionlogic.core.h2

import liquibase.action.core.AddAutoIncrementActionTest
import liquibase.actionlogic.core.AddAutoIncrementLogicTest
import liquibase.database.core.h2.H2ConnectionSupplier

class AddAutoIncrementLogicH2Test extends AddAutoIncrementLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddAutoIncrementActionTest(), new H2ConnectionSupplier()) == """
AddAutoIncrementAction.autoIncrementInformation.incrementBy is not supported
AddAutoIncrementAction.column is required
AddAutoIncrementAction.column.container is required
AddAutoIncrementAction.dataType is required
""".trim()
    }
}

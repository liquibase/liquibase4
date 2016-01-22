package liquibase.actionlogic.core.h2

import liquibase.action.core.AddColumnsActionTest
import liquibase.actionlogic.core.AddColumnsLogicTest
import liquibase.database.core.h2.H2ConnectionSupplier

class AddColumnsLogicH2Test extends AddColumnsLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddColumnsActionTest(), new H2ConnectionSupplier()) == """
AddColumnsAction does not support adding a primary key column
AddColumnsAction.columns cannot set both a default value and auto-increment
AddColumnsAction.columns is required
AddColumnsAction.columns.table.name is required
AddColumnsAction.columns.type is required
""".trim()
    }
}

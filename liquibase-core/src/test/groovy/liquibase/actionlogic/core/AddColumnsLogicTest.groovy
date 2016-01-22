package liquibase.actionlogic.core

import liquibase.action.core.AddColumnsActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class AddColumnsLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddColumnsActionTest(), new UnsupportedDatabaseSupplier()) == """
AddColumnsAction.columns cannot set both a default value and auto-increment
AddColumnsAction.columns is required
AddColumnsAction.columns.table.name is required
AddColumnsAction.columns.type is required
""".trim()
    }
}


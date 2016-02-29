package liquibase.actionlogic.core

import liquibase.action.core.AddForeignKeysActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.GenericConnectionSupplier

class AddForeignKeysLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddForeignKeysActionTest(), new GenericConnectionSupplier()) == """
AddForeignKeysAction.foreignKeys.columnChecks is required
AddForeignKeysAction.foreignKeys.deferrable is not supported
AddForeignKeysAction.foreignKeys.initiallyDeferred is not supported
AddForeignKeysAction.foreignKeys.referencedTable is required
AddForeignKeysAction.foreignKeys.relation is required
AddForeignKeysAction.foreignKeys: cannot specify a different foreign key schema
AddForeignKeysAction: liquibase.actionlogic.core.AddForeignKeysLogic tried to handle 'addForeignKeys()' but returned no actions to run
""".trim()
    }
}

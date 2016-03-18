package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AddForeignKeysLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddForeignKeysAction.foreignKeys is required
AddForeignKeysAction.foreignKeys.columnChecks is required
AddForeignKeysAction.foreignKeys.deferrable is not supported
AddForeignKeysAction.foreignKeys.initiallyDeferred is not supported
AddForeignKeysAction.foreignKeys.referencedTable is required
AddForeignKeysAction.foreignKeys.relation is required
AddForeignKeysAction.foreignKeys: cannot specify a different foreign key schema
""".trim()
    }
}

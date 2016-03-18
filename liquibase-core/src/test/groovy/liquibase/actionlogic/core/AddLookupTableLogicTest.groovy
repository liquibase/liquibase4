package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AddLookupTableLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddLookupTableAction.existingColumn is required
AddLookupTableAction.newColumn is required
AddLookupTableAction: Step error: AddForeignKeysAction.foreignKeys: cannot specify a different foreign key schema
AddLookupTableAction: Step error: CreateTableAction.columns.type is required
""".trim()
    }
}
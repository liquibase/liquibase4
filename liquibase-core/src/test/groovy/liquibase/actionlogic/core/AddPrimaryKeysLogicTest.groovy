package liquibase.actionlogic.core

import liquibase.action.core.AddPrimaryKeysActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.GenericConnectionSupplier

class AddPrimaryKeysLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
AddPrimaryKeysAction.primaryKeys is required
AddPrimaryKeysAction.primaryKeys.clustered: not supported
AddPrimaryKeysAction.primaryKeys.columns is required
AddPrimaryKeysAction.primaryKeys.columns.name is required
AddPrimaryKeysAction.primaryKeys.relation is required
AddPrimaryKeysAction.primaryKeys.tablespace is not supported
""".trim()
    }
}

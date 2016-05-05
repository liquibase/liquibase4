package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AddPrimaryKeysLogicTest extends AbstractActionLogicTest {
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

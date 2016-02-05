package liquibase.actionlogic.core

import liquibase.action.core.AddPrimaryKeysActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class AddPrimaryKeysLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddPrimaryKeysActionTest(), new UnsupportedDatabaseSupplier()) == """
AddPrimaryKeysAction.primaryKeys is required
AddPrimaryKeysAction.primaryKeys.columns is required
AddPrimaryKeysAction.primaryKeys.columns.name is required
AddPrimaryKeysAction.primaryKeys.table is required
AddPrimaryKeysAction.primaryKeys.tablespace is not supported
AddPrimaryKeysAction.primaryKeys: adding a clustered primary key is not supported
""".trim()
    }
}
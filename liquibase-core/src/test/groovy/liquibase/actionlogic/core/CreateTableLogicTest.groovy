package liquibase.actionlogic.core

import liquibase.action.core.CreateTableActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class CreateTableLogicTest extends AbstractLogicTest {
    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new CreateTableActionTest(), new UnsupportedDatabaseSupplier()) == """
CreateTableAction.columns is required
CreateTableAction.columns.name is required
CreateTableAction.columns.type is required
CreateTableAction.foreignKeys cannot specify a different foreign key schema
CreateTableAction.foreignKeys.columnChecks is required
CreateTableAction.foreignKeys.initiallyDeferred is not supported
CreateTableAction.primaryKey.columns is required
CreateTableAction.primaryKey.table is required
CreateTableAction.table is required
CreateTableAction.table.name is required
CreateTableAction.table.tablespace is not supported
CreateTableAction.uniqueConstraints.columns is required
CreateTableAction.uniqueConstraints.deferrable is not supported
CreateTableAction.uniqueConstraints.disabled is not supported
CreateTableAction.uniqueConstraints.initiallyDeferred is not supported
CreateTableAction.uniqueConstraints.tablespace is not supported
CreateTableAction: a default value on an auto-increment column is not supported
CreateTableAction: a nullable auto-increment column is not supported
""".trim()
    }
}

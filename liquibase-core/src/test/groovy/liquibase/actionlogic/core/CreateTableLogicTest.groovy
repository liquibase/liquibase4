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
CreateTableAction.columns: auto-increment columns must be primary keys
CreateTableAction.columns: cannot add a multi-column primary key and mark a column as auto-increment
CreateTableAction.columns: cannot set both a default value and auto-increment
CreateTableAction.columns: primary key columns cannot be nullable
CreateTableAction.foreignKeys.columnChecks is required
CreateTableAction.foreignKeys.initiallyDeferred is not supported
CreateTableAction.foreignKeys.referencedTable is required
CreateTableAction.foreignKeys.table is required
CreateTableAction.primaryKey.columns is required
CreateTableAction.primaryKey.table is required
CreateTableAction.primaryKey.tablespace is not supported
CreateTableAction.primaryKey: adding a clustered primary key is not supported
CreateTableAction.table is required
CreateTableAction.table.name is required
CreateTableAction.table.tablespace is not supported
CreateTableAction.uniqueConstraints.columns is required
CreateTableAction.uniqueConstraints.deferrable is not supported
CreateTableAction.uniqueConstraints.disabled is not supported
CreateTableAction.uniqueConstraints.initiallyDeferred is not supported
CreateTableAction.uniqueConstraints.table is required
CreateTableAction.uniqueConstraints.tablespace is not supported
CreateTableAction: a nullable auto-increment column is not supported
""".trim()
    }
}

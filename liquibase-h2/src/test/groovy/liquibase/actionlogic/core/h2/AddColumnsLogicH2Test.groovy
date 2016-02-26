package liquibase.actionlogic.core.h2

import liquibase.action.core.AddColumnsActionTest
import liquibase.actionlogic.core.AddColumnsLogicTest
import liquibase.database.core.h2.H2ConnectionSupplier

class AddColumnsLogicH2Test extends AddColumnsLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddColumnsActionTest(), new H2ConnectionSupplier()) == """
AddColumnsAction.columns is required
AddColumnsAction.columns.autoIncrementInformation is not supported
AddColumnsAction.columns.name is required
AddColumnsAction.columns.relation is required
AddColumnsAction.columns.type is required
AddColumnsAction.columns: auto-increment columns must be primary keys
AddColumnsAction.columns: cannot add a multi-column primary key and mark a column as auto-increment
AddColumnsAction.columns: cannot set both a default value and auto-increment
AddColumnsAction.columns: primary key columns cannot be nullable
AddColumnsAction.foreignKeys.columnChecks is required
AddColumnsAction.foreignKeys.initiallyDeferred is not supported
AddColumnsAction.primaryKey is not supported
AddColumnsAction.primaryKey.columns is required
AddColumnsAction.primaryKey.name is not supported
AddColumnsAction.primaryKey.relation is required
AddColumnsAction.primaryKey.tablespace is not supported
AddColumnsAction.primaryKey: adding a clustered primary key is not supported
AddColumnsAction.uniqueConstraints.relation is required
AddColumnsAction: ON DELETE NO ACTION is not supported
AddColumnsAction: ON UPDATE NO ACTION is not supported
AddColumnsAction: a nullable auto-increment column is not supported
""".trim()
    }
}

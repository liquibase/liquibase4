package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class AddColumnsLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddColumnsAction.columns is required
AddColumnsAction.columns.name is required
AddColumnsAction.columns.relation is required
AddColumnsAction.columns.type is required
AddColumnsAction.columns: auto-increment columns must be primary keys
AddColumnsAction.columns: cannot add a multi-column primary key and mark a column as auto-increment
AddColumnsAction.columns: cannot set both a default value and auto-increment
AddColumnsAction.columns: primary key columns cannot be nullable
AddColumnsAction.foreignKeys.columnChecks is required
AddColumnsAction.foreignKeys.initiallyDeferred is not supported
AddColumnsAction.primaryKey.clustered: not supported
AddColumnsAction.primaryKey.columns is required
AddColumnsAction.primaryKey.columns: 'DESC'
AddColumnsAction.primaryKey.relation is required
AddColumnsAction.primaryKey.tablespace is not supported
AddColumnsAction.uniqueConstraints.relation is required
AddColumnsAction: a nullable auto-increment column is not supported
AddColumnsAction: delete rule SET DEFAULT is not supported
AddColumnsAction: update rule SET DEFAULT is not supported
""".trim()
    }
}

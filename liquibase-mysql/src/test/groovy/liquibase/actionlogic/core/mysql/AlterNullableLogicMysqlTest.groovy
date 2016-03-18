package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class AlterNullableLogicMysqlTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
AlterNullableAction.column is required
AlterNullableAction.columnDataType is required
AlterNullableAction.constraintName is not supported
AlterNullableAction.nullable is required
AlterNullableAction.valueForExistingNulls: only valid when nullable=false
""".trim()
    }
}

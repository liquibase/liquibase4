package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AlterNullableLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterNullableAction.column is required
AlterNullableAction.constraintName is not supported
AlterNullableAction.nullable is required
AlterNullableAction.valueForExistingNulls: only valid when nullable=false
""".trim()
    }
}

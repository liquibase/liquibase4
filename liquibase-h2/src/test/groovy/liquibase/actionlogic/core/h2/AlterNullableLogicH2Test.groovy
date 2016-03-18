package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest

class AlterNullableLogicH2Test extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return """
AlterNullableAction.column is required
AlterNullableAction.constraintName is not supported
AlterNullableAction.nullable is required
AlterNullableAction.valueForExistingNulls: only valid when nullable=false
""".trim()
    }
}

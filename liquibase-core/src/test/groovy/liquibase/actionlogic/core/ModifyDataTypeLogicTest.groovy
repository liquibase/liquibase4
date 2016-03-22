package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class ModifyDataTypeLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
ModifyDataTypeAction.column is required
ModifyDataTypeAction.column.container is required
ModifyDataTypeAction.column.name is required
ModifyDataTypeAction.newDataType is required
""".trim()
    }
}

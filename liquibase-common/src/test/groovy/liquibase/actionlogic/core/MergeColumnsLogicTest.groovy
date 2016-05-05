package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class MergeColumnsLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
MergeColumnsAction.column1Name is required
MergeColumnsAction.column2Name is required
MergeColumnsAction.finalColumnName is required
MergeColumnsAction.finalColumnType is required
MergeColumnsAction.relation is required
""".trim()
    }
}

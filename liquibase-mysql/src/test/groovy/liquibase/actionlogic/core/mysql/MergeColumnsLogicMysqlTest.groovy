package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class MergeColumnsLogicMysqlTest extends AbstractActionLogicTest {
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

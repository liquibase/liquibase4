package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class InsertDataLogicMysqlTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
InsertDataAction.rows is required
InsertDataAction: there must be at least one column in rowData not in columnsForUpdateCheck
""".trim()
    }
}

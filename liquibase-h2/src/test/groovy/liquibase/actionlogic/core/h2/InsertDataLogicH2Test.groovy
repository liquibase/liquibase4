package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest
import spock.lang.Specification

class InsertDataLogicH2Test extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
InsertDataAction.data is required
InsertDataAction: there must be at least one column in rowData not in columnsForUpdateCheck
""".trim()
    }
}

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DeleteDataLogicTest extends AbstractActionLogicTest{
    @Override
    protected String getExpectedValidationErrors() {
        return """
DeleteDataAction.relation is required
""".trim()
    }
}

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class SelectDataLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
SelectDataAction.columns is required
SelectDataAction.columns.name is required
SelectDataAction.joins.relation is required
SelectDataAction.relation is required
""".trim()
    }
}

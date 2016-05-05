package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class InsertDataFromQueryLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
InsertDataFromQueryAction.destination is required
InsertDataFromQueryAction.destination.name is required
InsertDataFromQueryAction.query is required
InsertDataFromQueryAction.query: SelectDataAction.relation is required
""".trim()
    }
}
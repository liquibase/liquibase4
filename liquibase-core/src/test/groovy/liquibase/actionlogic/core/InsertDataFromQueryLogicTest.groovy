package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest
import spock.lang.Specification

class InsertDataFromQueryLogicTest extends AbstractLogicTest {

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
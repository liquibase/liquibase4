package liquibase.action

import liquibase.util.StringClauses
import spock.lang.Specification

class QuerySqlActionTest extends Specification { //does not extend AbstractAction because it is a common low-level action that is already well-tested

    def "constructor and describe"() {
        expect:
        new QuerySqlAction("select x").describe() == "select x"
        new QuerySqlAction(new StringClauses().append("select").append("x")).describe() == "select x"
    }
}
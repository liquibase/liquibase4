package liquibase.action

import liquibase.util.StringClauses
import spock.lang.Specification

class ExecuteSqlActionTest extends Specification { //does not extend AbstractAction because it is a common low-level action that is already well-tested

    def "constructor and describe"() {
        expect:
        new ExecuteSqlAction("select 3").describe() == "select 3"
        new ExecuteSqlAction(new StringClauses().append("select").append("3")).describe() == "select 3"
    }
}

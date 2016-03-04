package liquibase.action

import liquibase.util.StringClauses
import spock.lang.Specification

class UpdateSqlActionTest extends Specification {
    //does not extend AbstractAction because it is a common low-level action that is already well-tested

    def "constructor and describe"() {
        expect:
        new UpdateSqlAction("update x").describe() == "update x"
        new UpdateSqlAction(new StringClauses().append("update").append("x")).describe() == "update x"
    }
}
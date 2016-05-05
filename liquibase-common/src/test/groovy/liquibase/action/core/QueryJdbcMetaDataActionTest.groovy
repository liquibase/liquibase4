package liquibase.action.core

import spock.lang.Specification

class QueryJdbcMetaDataActionTest extends Specification { //does not extend AbstractActionTest since the actual use of this action is tested with other actions

    def "constructor"() {
        expect:
        new QueryJdbcMetaDataAction("testMethod").describe() == "testMethod()"
        new QueryJdbcMetaDataAction("testMethod", null).describe() == "testMethod()"
        new QueryJdbcMetaDataAction("testMethod", 1, 2, "c").describe() == "testMethod(1, 2, c)"
        new QueryJdbcMetaDataAction("testMethod", "a", null, "b").describe() == "testMethod(a, null, b)"
    }
}

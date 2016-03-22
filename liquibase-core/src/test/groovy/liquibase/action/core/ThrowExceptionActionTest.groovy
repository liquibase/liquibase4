package liquibase.action.core

import spock.lang.Specification

class ThrowExceptionActionTest extends Specification { //not normal ActionTest

    def "constructor"() {
        expect:
        new ThrowExceptionAction("test message").exception.toString() == "liquibase.action.core.ThrowExceptionAction\$ThrowExceptionActionException: test message"
    }
}

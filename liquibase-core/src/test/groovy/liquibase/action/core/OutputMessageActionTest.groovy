package liquibase.action.core

import spock.lang.Specification

class OutputMessageActionTest extends Specification { //not a normal ActionTest because it doesn't interact with the database

    def "constructor"() {
        expect:
        new OutputMessageAction(OutputMessageAction.MessageTarget.DEBUG, "test message").toString() == "outputMessage(message=test message, target=DEBUG)"
    }
}

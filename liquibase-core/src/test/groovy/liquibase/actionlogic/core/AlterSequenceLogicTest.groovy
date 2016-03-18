package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AlterSequenceLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
AlterSequenceAction.ordered is not supported
AlterSequenceAction.sequence is required
AlterSequenceAction.sequence.name is required
AlterSequenceAction: no alterations to make
""".trim()
    }
}

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropSequencesLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropSequencesAction.sequences is required
DropSequencesAction.sequences.name is required
""".trim()
    }
}

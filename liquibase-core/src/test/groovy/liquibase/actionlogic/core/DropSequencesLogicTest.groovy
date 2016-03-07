package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropSequencesLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropSequencesAction.sequences is required
DropSequencesAction.sequences.name is required
""".trim()
    }
}

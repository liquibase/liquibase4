package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest

class AlterRemarksLogicH2Test extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterRemarksAction.object is required
""".trim();
    }
}

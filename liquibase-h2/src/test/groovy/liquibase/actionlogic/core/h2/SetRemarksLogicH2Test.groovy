package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractActionLogicTest

class SetRemarksLogicH2Test extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
SetRemarksAction.object is required
""".trim();
    }
}

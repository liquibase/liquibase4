package liquibase.actionlogic.core.h2

import liquibase.actionlogic.AbstractLogicTest

class SetRemarksLogicH2Test extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
SetRemarksAction.object is required
""".trim();
    }
}

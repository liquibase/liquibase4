package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class RollbackLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return "";
    }
}

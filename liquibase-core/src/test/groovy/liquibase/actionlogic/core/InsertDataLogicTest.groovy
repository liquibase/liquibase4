package liquibase.actionlogic.core

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractLogicTest

class InsertDataLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return null;
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null; //no AbstractActionTest
    }
}
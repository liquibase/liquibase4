package liquibase.actionlogic.core

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractActionLogicTest

class ExecuteSqlLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return null;
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null; //no AbstractActionTest
    }
}

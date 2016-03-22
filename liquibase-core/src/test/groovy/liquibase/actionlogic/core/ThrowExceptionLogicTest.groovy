package liquibase.actionlogic.core

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractActionLogicTest

public class ThrowExceptionLogicTest extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return ""
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null;
    }
}

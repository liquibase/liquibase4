package liquibase.actionlogic.core

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractActionLogicTest;

public class UpdateSqlLogicTest extends AbstractActionLogicTest{

    @Override
    String getExpectedValidationErrors() {
        return """
        """.trim()
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null; //no AbstractActionTest
    }


}

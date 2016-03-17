package liquibase.actionlogic.core

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.AbstractLogicTest;

public class UpdateSqlLogicTest extends AbstractLogicTest{

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

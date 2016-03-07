package liquibase.actionlogic.core;

import liquibase.actionlogic.AbstractLogicTest;

public class CommitLogicTest extends AbstractLogicTest {
    @Override
    public String getExpectedValidationErrors() {
        return ""; //expect nothing to validate
    }
}

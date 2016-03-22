package liquibase.actionlogic.core;

import liquibase.actionlogic.AbstractActionLogicTest;

public class CommitLogicTest extends AbstractActionLogicTest {
    @Override
    public String getExpectedValidationErrors() {
        return ""; //expect nothing to validate
    }
}

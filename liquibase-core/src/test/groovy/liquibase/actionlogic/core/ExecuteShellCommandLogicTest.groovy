package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest
import spock.lang.Specification

class ExecuteShellCommandLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
ExecuteShellCommandAction.executable is required
""".trim()
    }
}

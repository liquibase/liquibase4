package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest
import spock.lang.Specification

class CreateStoredProceduresLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
CreateStoredProceduresAction.procedures is required
CreateStoredProceduresAction.procedures.body is required
""".trim()
    }
}

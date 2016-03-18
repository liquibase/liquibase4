package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest
import spock.lang.Specification

class DropStoredProceduresLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
DropStoredProceduresAction.procedures is required
DropStoredProceduresAction.procedures.name is required
""".trim()
    }
}

package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest
import spock.lang.Specification

class AlterTableLogicTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterTableAction.newDefinition is required
AlterTableAction.table is required
""".trim()
    }
}

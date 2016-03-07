package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest
import spock.lang.Specification

class AlterColumnLogicTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AlterColumnAction.column is required
AlterColumnAction.column.container is required
AlterColumnAction.column.name is required
AlterColumnAction.newDefinition is required
""".trim()
    }
}

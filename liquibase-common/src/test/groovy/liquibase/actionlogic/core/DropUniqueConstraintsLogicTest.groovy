package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropUniqueConstraintsLogicTest extends AbstractActionLogicTest {
    @Override
    protected String getExpectedValidationErrors() {
        return """
DropUniqueConstraintsAction.constraints is required
DropUniqueConstraintsAction.constraints.container is required
DropUniqueConstraintsAction.constraints.name is required
""".trim()
    }
}

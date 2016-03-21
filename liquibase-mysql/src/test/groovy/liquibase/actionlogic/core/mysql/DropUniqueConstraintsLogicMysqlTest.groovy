package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class DropUniqueConstraintsLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    protected String getExpectedValidationErrors() {
        return """
DropUniqueConstraintsAction.constraints is required
DropUniqueConstraintsAction.constraints.container is required
DropUniqueConstraintsAction.constraints.name is required
""".trim()
    }
}

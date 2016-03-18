package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropViewsLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropViewsAction.views is required
DropViewsAction.views.name is required
""".trim()
    }
}
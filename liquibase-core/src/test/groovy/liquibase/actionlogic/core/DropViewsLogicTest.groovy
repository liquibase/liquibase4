package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropViewsLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropViewsAction.views is required
DropViewsAction.views.name is required
""".trim()
    }
}
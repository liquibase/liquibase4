package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class CreateViewsLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
CreateViewsAction.views is required
CreateViewsAction.views.definition is required
CreateViewsAction.views.name is required
""".trim()
    }
}
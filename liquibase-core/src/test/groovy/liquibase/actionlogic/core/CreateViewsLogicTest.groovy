package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class CreateViewsLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
CreateViewsAction.views is required
CreateViewsAction.views.definition is required
CreateViewsAction.views.name is required
""".trim()
    }
}
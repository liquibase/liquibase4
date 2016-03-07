package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractLogicTest

class DropIndexesLogicTest extends AbstractLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropIndexesAction.indexes is required
DropIndexesAction.indexes.indexSchema is not supported
DropIndexesAction.indexes.name is required
""".trim()
    }
}

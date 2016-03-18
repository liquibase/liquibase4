package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class DropIndexesLogicTest extends AbstractActionLogicTest {
    @Override
    String getExpectedValidationErrors() {
        return """
DropIndexesAction.indexes is required
DropIndexesAction.indexes.indexSchema is not supported
DropIndexesAction.indexes.name is required
""".trim()
    }
}

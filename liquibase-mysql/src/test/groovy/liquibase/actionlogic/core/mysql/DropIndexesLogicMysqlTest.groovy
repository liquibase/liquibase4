package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractActionLogicTest

class DropIndexesLogicMysqlTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
DropIndexesAction.indexes is required
DropIndexesAction.indexes.container is required
DropIndexesAction.indexes.indexSchema is not supported
DropIndexesAction.indexes.name is required
""".trim()
    }
}

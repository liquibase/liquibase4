package liquibase.actionlogic.core.mysql

import liquibase.actionlogic.AbstractLogicTest

class CreateIndexesLogicMysqlTest extends AbstractLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
CreateIndexesAction.indexes is required
CreateIndexesAction.indexes.clustered is not supported
CreateIndexesAction.indexes.columns is required
CreateIndexesAction.indexes.columns: Cannot specify DESC indexes
CreateIndexesAction.indexes.indexSchema is not supported
CreateIndexesAction.indexes.name is required
CreateIndexesAction.indexes.relation is required
CreateIndexesAction.indexes.tablespace is not supported
""".trim()
    }
}

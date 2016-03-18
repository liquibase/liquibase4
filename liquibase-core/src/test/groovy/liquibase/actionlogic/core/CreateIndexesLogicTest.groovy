package liquibase.actionlogic.core;

import liquibase.actionlogic.AbstractActionLogicTest;

public class CreateIndexesLogicTest extends AbstractActionLogicTest {

    @Override
    public String getExpectedValidationErrors() {
        return """
CreateIndexesAction.indexes is required
CreateIndexesAction.indexes.clustered is not supported
CreateIndexesAction.indexes.columns is required
CreateIndexesAction.indexes.indexSchema is not supported
CreateIndexesAction.indexes.relation is required
CreateIndexesAction.indexes.tablespace is not supported
""".trim();
    }
}

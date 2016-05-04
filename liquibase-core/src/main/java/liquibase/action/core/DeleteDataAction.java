package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringClauses;

public class DeleteDataAction extends AbstractAction {

    public RelationReference relation;

    /**
     * Default where clauses are " AND " joined.
     * This can be replaced if you need a different standard operator, or just add a single StringClauses element with a different operator joining it's own clauses.
     */
    public StringClauses where = new StringClauses(" AND ");

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DeleteDataAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"delete"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
            }
        };
    }
}

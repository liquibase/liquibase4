package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Drops all foreign key constraints on a table.
 */
public class DropAllForeignKeysAction extends AbstractAction {

    public RelationReference table;

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropAllForeignKeysAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropAllForeignKeyConstraints"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode table = convertToRelationReferenceNode("baseTableCatalogName", "baseTableSchemaName", "baseTableName", actionNode);
                if (table != null) {
                    table.rename("table");
                }

            }
        };
    }
}

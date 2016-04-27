package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringClauses;

/**
 * Renames a column in a table or view.
 */
public class RenameColumnAction extends AbstractAction {
    public RelationReference relation;

    public String oldName;
    public String newName;

    public StringClauses columnDefinition;
    public String remarks;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {

                new AbstractActionPreprocessor(RenameColumnAction.class) {

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        actionNode.renameChildren("oldColumnName", "oldName");
                        actionNode.renameChildren("newColumnName", "newName");
                        actionNode.renameChildren("columnDataType", "columnDefinition");
                    }
                }
        };
    }
}

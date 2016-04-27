package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Renames a view. {@link liquibase.actionlogic.ActionLogic} implementations should handle the case where the schema changes as part of the rename.
 */
public class RenameViewAction extends AbstractAction {
    public RelationReference oldName;
    public RelationReference newName;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(RenameViewAction.class) {
                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode oldView = convertToRelationReferenceNode(null, null, "oldViewName", actionNode).rename("oldName");
                        ParsedNode newView = convertToRelationReferenceNode(null, null, "newViewName", actionNode).rename("newName");

                        ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode).rename("container");
                        if (schema != null) {
                            schema.moveTo(oldView);
                            schema.copyTo(newView);
                        }


                    }
                }
        };
    }
}

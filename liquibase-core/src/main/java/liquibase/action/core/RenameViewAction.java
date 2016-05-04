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
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(RenameViewAction.class) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode);
                actionNode.renameChildren("oldViewName", "oldName");
                actionNode.renameChildren("newViewName", "newName");

                if (schema != null) {
                    schema.rename("container");
                    ParsedNode oldName = actionNode.getChild("oldName", false);
                    ParsedNode newName = actionNode.getChild("newName", false);

                    if (oldName != null) {
                        schema.copyTo(oldName);
                    }
                    if (newName != null) {
                        schema.copyTo(newName);
                    }
                    schema.remove();
                }

            }
        };
    }
}

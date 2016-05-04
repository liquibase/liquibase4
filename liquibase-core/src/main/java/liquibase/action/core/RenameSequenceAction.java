package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.SequenceReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Renames a sequence. {@link liquibase.actionlogic.ActionLogic} implementations should handle the case where the schema changes as part of the rename.
 */
public class RenameSequenceAction extends AbstractAction {
    public SequenceReference oldName;
    public SequenceReference newName;

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(RenameSequenceAction.class) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                actionNode.renameChildren("oldSequenceName", "oldName");
                actionNode.renameChildren("newSequenceName", "newName");

                ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode);

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

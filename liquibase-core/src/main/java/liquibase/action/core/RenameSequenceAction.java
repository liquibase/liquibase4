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
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(RenameSequenceAction.class) {
                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode oldName = convertToSequenceReferenceNode("catalogName", "schemaName", "oldSequenceName", actionNode);
                        if (oldName != null) {
                            oldName.rename("oldName");
                            ParsedNode newName = actionNode.getChild("newSequenceName", false);
                            if (newName != null) {
                                newName.rename("newName");
                                ParsedNode nameNode = newName.addChild("name");
                                newName.moveValue(nameNode);
                                ParsedNode container = oldName.getChild("container", false);
                                if (container != null) {
                                    container.copyTo(newName);
                                }
                            }
                        }
                    }
                }
        };
    }
}

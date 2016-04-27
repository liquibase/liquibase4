package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.SequenceReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Action to drop existing sequence(s).
 */
public class DropSequencesAction extends AbstractAction {

    public List<SequenceReference> sequences = new ArrayList<>();

    public DropSequencesAction() {

    }

    public DropSequencesAction(SequenceReference... sequences) {
        if (sequences != null) {
            this.sequences.addAll(Arrays.asList(sequences));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(DropSequencesAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {
                                "dropSequence"
                        };
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToSequenceReferenceNode("catalogName", "schemaName", "sequenceName", actionNode);
                        actionNode.moveChildren("sequence", actionNode.getChild("sequences", true));
                    }
                }
        };
    }
}

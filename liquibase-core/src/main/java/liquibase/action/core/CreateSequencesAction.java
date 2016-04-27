package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Sequence;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add sequences to the database
 */
public class CreateSequencesAction extends AbstractAction {

    public List<Sequence> sequences = new ArrayList<>();

    public CreateSequencesAction() {
    }

    public CreateSequencesAction(Sequence... sequences) {
        if (sequences != null) {
            this.sequences.addAll(Arrays.asList(sequences));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(CreateSequencesAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"createSequence"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode sequenceName = actionNode.getChild("sequenceName", false);
                        if (sequenceName != null) {
                            ParsedNode sequence = actionNode.addChild("sequence");
                            actionNode.moveChildren("cacheSize", sequence);
                            actionNode.moveChildren("cycle", sequence);
                            actionNode.moveChildren("incrementBy", sequence);
                            actionNode.moveChildren("maxValue", sequence);
                            actionNode.moveChildren("minValue", sequence);
                            actionNode.moveChildren("ordered", sequence);
                            actionNode.moveChildren("startValue", sequence);
                            actionNode.moveChildren("sequenceName", sequence);
                            actionNode.moveChildren("schemaName", sequence);
                            actionNode.moveChildren("catalogName", sequence);

                            sequence.renameChildren("sequenceName", "name");
                            convertToSchemaReferenceNode("catalogName", "schemaName", sequence);
                        }

                        actionNode.moveChildren("sequence", actionNode.getChild("sequences", true));
                    }
                }
        };
    }
}

package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;

import java.util.List;

/**
 * {@link liquibase.changelog.ChangeSet} related preprocessing.
 */
public class ChangeSetPreprocessor extends AbstractParsedNodePreprocessor {

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeAfter() {
        return new Class[]{
                ChangeLogPreprocessor.class,
        };
    }

    /**
     * Moves all nodes that end in "Action" to the "actions" collection.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        List<ParsedNode> changeSets = node.getChild("changeLogEntries", true).getChildren("changeSet", false);

        for (ParsedNode changeSet : changeSets) {
            ParsedNode actions = changeSet.getChild("actions", true);

            for (ParsedNode possibleAction : changeSet.getChildren()) {
                if (possibleAction.name.endsWith("Action")) {
                    possibleAction.moveTo(actions);
                }
            }
        }
    }
}

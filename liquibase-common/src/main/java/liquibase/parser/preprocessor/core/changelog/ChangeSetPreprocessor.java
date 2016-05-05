package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.ActionFactory;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.CollectionUtil;

import java.util.List;
import java.util.SortedSet;

/**
 * {@link liquibase.changelog.ChangeSet} related preprocessing.
 */
public class ChangeSetPreprocessor extends AbstractParsedNodePreprocessor {

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeAfter() {
        return CollectionUtil.union(Class.class, super.mustBeAfter(), ChangeLogPreprocessor.class);
    }

    /**
     * Moves all nodes that match an action name to the "actions" collection.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        List<ParsedNode> changeSets = node.getChild("changeLogEntries", true).getChildren("changeSet", false);

        SortedSet<String> allActions = scope.getSingleton(ActionFactory.class).getAllActionNames();

        for (ParsedNode changeSet : changeSets) {
            changeSet.removeChildren("empty");

            ParsedNode actions = changeSet.getChild("actions", true);

            for (ParsedNode possibleAction : changeSet.getChildren()) {
                if (allActions.contains(possibleAction.getName())) {
                    possibleAction.moveTo(actions);
                }
            }
        }
    }
}

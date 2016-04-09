package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;

/**
 * {@link liquibase.changelog.ChangeLog} related preprocessing.
 */
public class ChangeLogPreprocessor extends AbstractParsedNodePreprocessor {

    private ParsedNode.ParsedNodeFilter legacyQuotingStrategyNodeFilter = new ParsedNode.ParsedNodeFilter() {
        @Override
        public boolean matches(ParsedNode node) {
            return (node.name.equals("quotingStrategy") || node.name.equals("objectQuotingStrategy"))
                    && node.value != null && node.value.toString().equalsIgnoreCase("legacy");
        }
    };

    /**
     * Remove the now-unused legacy quoting strategy.
     * Move changeSet nodes into the changeLogEntries collection.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        node.removeChildren("schemaLocation");

        for (ParsedNode quotingStrategy : node.getChildren(legacyQuotingStrategyNodeFilter, true)) {
            quotingStrategy.remove();
        }

        ParsedNode changeLogEntries = node.getChild("changeLogEntries", true);
        for (ParsedNode changeSetNode : node.getChildren("changeSet", false)) {
            changeSetNode.moveTo(changeLogEntries);
        }
    }
}

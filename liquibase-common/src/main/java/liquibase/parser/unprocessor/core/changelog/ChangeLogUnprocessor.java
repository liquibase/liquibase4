package liquibase.parser.unprocessor.core.changelog;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;

public class ChangeLogUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public void unprocess(ParsedNode node, Scope scope) throws ParseException {
        if (node.getName().equals("changeLog")) {
            node.removeChildren("physicalPath");

            ParsedNode changeLogEntries = node.getChild("changeLogEntries", false);
            if (changeLogEntries != null) {
                changeLogEntries.moveChildren("changeSet", node);

                if (changeLogEntries.isEmpty()) {
                    changeLogEntries.remove();
                }
            }
        }
    }
}

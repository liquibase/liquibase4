package liquibase.parser.unprocessor.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;

public class SnapshotUnprocessor extends AbstractParsedNodeUnprocessor {
    @Override
    public void unprocess(ParsedNode node, Scope scope) throws ParseException {
        if (node.getName().equals("snapshot")) {
            for (ParsedNode items : node.getChildren("items", false)) {
                markChildrenAsAttributes(items, "type");
            }
        }
    }
}

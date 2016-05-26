package liquibase.parser.unprocessor.core.changelog;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

public class ChangeSetUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public Class<? extends ParsedNodeUnprocessor>[] mustBeAfter() {
        return new Class[]{
                ChangeLogUnprocessor.class
        };
    }

    @Override
    public void unprocess(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", false)) {
            markChildrenAsAttributes(changeSet, "id", "author");

            changeSet.removeChildren("logicalPath");
        }
    }
}

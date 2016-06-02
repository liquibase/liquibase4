package liquibase.parser.unprocessor.core.item;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;

public class ItemUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public void unprocess(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode itemNode : node.getChildren(Item.class, true)) {
            markChildrenAsAttributes(itemNode, "name");
        }
    }
}

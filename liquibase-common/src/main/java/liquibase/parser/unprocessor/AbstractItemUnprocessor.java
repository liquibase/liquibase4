package liquibase.parser.unprocessor;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.Item;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.core.item.ItemReferenceUnprocessor;

abstract public class AbstractItemUnprocessor extends AbstractParsedNodeUnprocessor {

    private final Class<? extends Item> itemType;

    public AbstractItemUnprocessor(Class<? extends Item> itemType) {
        this.itemType = itemType;
    }

    @Override
    public Class<? extends ParsedNodeUnprocessor>[] mustBeAfter() {
        return new Class[] {
                ItemReferenceUnprocessor.class
        };
    }

    @Override
    public void unprocess(ParsedNode node, String outputPath, Scope scope) throws ParseException {
        for (ParsedNode typeNode : node.getChildren(this.itemType, true)) {
            unprocessItem(typeNode, scope);
        }
    }

    abstract protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException;
}

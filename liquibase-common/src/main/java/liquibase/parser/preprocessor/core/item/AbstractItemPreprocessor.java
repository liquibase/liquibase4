package liquibase.parser.preprocessor.core.item;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.Item;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.util.StringUtil;

public abstract class AbstractItemPreprocessor extends AbstractParsedNodePreprocessor {

    protected final String standardNodeName;

    public AbstractItemPreprocessor(Class<? extends Item> itemType) {
        standardNodeName = createStandardNodeName(itemType);
    }

    protected String createStandardNodeName(Class<? extends Item> itemType) {
        return StringUtil.lowerCaseFirst(itemType.getSimpleName());
    }

    /**
     * Default implementation calls {@link #processItemNode(ParsedNode, Scope)} for each node that ends up matching {@link #standardNodeName}.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode itemNode : node.getChildren(standardNodeName, true)) {
            processItemNode(itemNode, scope);
        }
    }

    protected abstract void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException;

}

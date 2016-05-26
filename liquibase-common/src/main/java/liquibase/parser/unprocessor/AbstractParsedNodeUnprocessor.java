package liquibase.parser.unprocessor;

import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.AbstractPlugin;
import liquibase.util.CollectionUtil;

/**
 * Convenience base class for {@link ParsedNodeUnprocessor} implementations.
 */
public abstract class AbstractParsedNodeUnprocessor extends AbstractPlugin implements ParsedNodeUnprocessor {

    @Override
    public Class<? extends ParsedNodeUnprocessor>[] mustBeBefore() {
        return null;
    }

    @Override
    public Class<? extends ParsedNodeUnprocessor>[] mustBeAfter() {
        return null;
    }

    /**
     * Marks the given child node names as {@link liquibase.parser.ParsedNode.Marker#isAttribute}
     */
    protected void markChildrenAsAttributes(ParsedNode node, String... childNames) throws ParseException {
        for (String attributeName : CollectionUtil.createIfNull(childNames)) {
            ParsedNode child = node.getChild(attributeName, false);
            if (child != null) {
                child.addMarker(ParsedNode.Marker.isAttribute);
            }
        }
    }

}

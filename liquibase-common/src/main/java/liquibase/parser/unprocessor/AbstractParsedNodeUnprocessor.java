package liquibase.parser.unprocessor;

import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.xml.XmlParser;
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
     * Marks the given child node names as xml attributes for the {@link liquibase.parser.xml.XmlUnparser}
     */
    protected void markChildrenAsXmlAttributes(ParsedNode node, String... childNames) throws ParseException {
        for (String attributeName : CollectionUtil.createIfNull(childNames)) {
            ParsedNode child = node.getChild(attributeName, false);
            if (child != null && !child.hasChild(XmlParser.XML_ATTRIBUTE_PROPERTY)) {
                child.addChild(XmlParser.XML_ATTRIBUTE_PROPERTY).setValue(true);
            }
        }
    }

}

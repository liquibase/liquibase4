package liquibase.parser.unprocessor;

import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.AbstractPlugin;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtil;

import java.util.*;

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

    protected void markChildAsText(ParsedNode node, String childName) throws ParseException {
        ParsedNode childNode = node.getChild(childName, false);
        if (childNode != null) {
            childNode.removeMarker(ParsedNode.Marker.isAttribute);
            childNode.addMarker(ParsedNode.Marker.isText);
        }
    }

    protected ParsedNode convertListToCommaSeparatedAttribute(String collectionNodeName, ParsedNode objectNode) throws ParseException {
        ParsedNode collectionNode = objectNode.getChild(collectionNodeName, false);
        if (collectionNode == null) {
            return null;
        }
        List<String> values = new ArrayList<>();
        for (ParsedNode value : collectionNode.getChildren("value", false)) {
            if (value.getChildren().isEmpty()) {
                values.add(value.getValue(null, String.class));
                value.remove();
            }
        }

        if (!collectionNode.isEmpty()) {
            throw new ParseException("Unexpected children in " + collectionNodeName + " after converting values to string", collectionNode);
        }
        if (values.isEmpty()) {
            collectionNode.remove();

            return null;
        } else {
            collectionNode.setValue(StringUtil.join(values, ", "));
            collectionNode.addMarker(ParsedNode.Marker.isAttribute);
            return collectionNode;
        }
    }

//    protected void hoistListNodes(ParsedNode baseNode, String... collectionNodeNames) throws ParseException {
//        if (collectionNodeNames == null) {
//            return;
//        }
//
//        Set<String> namesToMove = new HashSet<>(Arrays.asList(collectionNodeNames));
//
//        for (ParsedNode child : new ArrayList<>(baseNode.getChildren())) {
//            if (namesToMove.contains(child.getName())) {
//                child.moveChildren(ParsedNode.ParsedNodeFilter.ALL, baseNode);
//                child.remove();
//            }
//        }
//    }

}

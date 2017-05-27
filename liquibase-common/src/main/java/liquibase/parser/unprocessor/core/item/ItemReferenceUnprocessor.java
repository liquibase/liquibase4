package liquibase.parser.unprocessor.core.item;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.ItemReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;
import liquibase.util.StringUtil;

public class ItemReferenceUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public void unprocess(ParsedNode node, String outputPath, Scope scope) throws ParseException {
        for (ParsedNode refNode : node.getChildren(ItemReference.class, true)) {
            ParsedNode parentNode = refNode.getParent();
            ParsedNode originalNode = refNode;

            String baseReferenceNodeName = null;
            while (refNode != null) {
                String containerType = refNode.getChildValue("type", String.class, false);
                if (containerType == null) {
                    break;
                } else {
                    Boolean virtual = refNode.getChildValue("virtual", Boolean.class, false);
                    if (virtual != null && !virtual) {
                        refNode.removeChildren("virtual");
                    } else {
                        break;
                    }

                    String attributeName = toNodeName(refNode.getName(), containerType, baseReferenceNodeName);
                    if (baseReferenceNodeName == null) {
                        baseReferenceNodeName = attributeName;
                    }

                    refNode.removeChildren("type");
                    refNode.renameChildren("name", attributeName);
                    for (ParsedNode nameNode : refNode.getChildren(attributeName, false)) {
                        nameNode.moveTo(parentNode);
                        nameNode.addMarker(ParsedNode.Marker.isAttribute);
                    }
                }

                refNode = refNode.getChild("container", false);
            }

            originalNode.remove();
        }
    }

    protected String toNodeName(String refNodeName, String type, String baseReferenceNodeName) {
        if (type == null) {
            return refNodeName;
        }
        String shortTypeName = type.replaceFirst(".*\\.", "");
        String lowerCaseTypeName = StringUtil.lowerCaseFirst(shortTypeName);

        String prefix = null;
        if (baseReferenceNodeName != null && baseReferenceNodeName.endsWith("Name") && baseReferenceNodeName.replaceAll("[a-z]", "").length() > 1) {
            prefix = baseReferenceNodeName.substring(0, baseReferenceNodeName.length() - 4); //strip off Name
        }

        if (lowerCaseTypeName.equals(refNodeName)) {
            if (prefix == null) {
                return lowerCaseTypeName + "Name";
            } else {
                return prefix + shortTypeName + "Name";
            }
        } else if (refNodeName.matches(".*" + shortTypeName)) {
            if (prefix == null) {
                return refNodeName + "Name";
            } else {
                return prefix + StringUtil.upperCaseFirst(refNodeName) + "Name";
            }
        } else if (refNodeName.equals("container") || refNodeName.equals("relation")) {
            if (prefix == null) {
                return lowerCaseTypeName + "Name";
            } else {
                return prefix + shortTypeName + "Name";
            }
        } else {
            return refNodeName + "Name";
        }
    }
}

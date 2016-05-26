package liquibase.parser;

import liquibase.plugin.AbstractPlugin;
import liquibase.util.StringUtil;

/**
 * Convenience base class for {@link Parser} implementations.
 */
public abstract class AbstractParser extends AbstractPlugin implements Parser {

    /**
     * Default implementation lists node name, value, and children names in a generic format.
     */
    @Override
    public String describeOriginal(ParsedNode parsedNode) {
        String returnString = "";
        ParsedNode nodeToPrint = parsedNode;

        if (nodeToPrint != null) {
            returnString += "near " + nodeToPrint.getOriginalName();
            if (nodeToPrint.getValue() != null) {
                returnString += "=\"" + nodeToPrint.getValue() + "\"";
            }
            if (nodeToPrint.getChildren().size() > 0) {
                returnString += " children: " + StringUtil.join(nodeToPrint.getChildren(), ", ", new StringUtil.StringUtilFormatter<ParsedNode>() {
                    @Override
                    public String toString(ParsedNode obj) {
                        return obj.getName();
                    }
                });
            }
        }

        while (nodeToPrint != null) {
            returnString += "\n" + StringUtil.indent("in " + nodeToPrint.getOriginalName());
            nodeToPrint = nodeToPrint.getOriginalParent();
        }

        return returnString;
    }
}

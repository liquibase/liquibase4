package liquibase.parser.mapping.core;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.ParsedNodeMapping;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;

/**
 * Converts parsed nodes to/from non-{@link ExtensibleObject} objects by running {@link ParsedNode#value} through {@link ObjectUtil#convert(Object, Class)}.
 */
public class PlainObjectParsedNodeMapping implements ParsedNodeMapping {

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (!ExtensibleObject.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode toParsedNode(Object objectToConvert, Class containerType, String containerAttribute, ParsedNode parentNode, Scope scope) throws ParseException {
        return parentNode.addChild(containerAttribute)
                .setValue(objectToConvert);
    }

    @Override
    public Object toObject(ParsedNode parsedNode, Class objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        try {
            if (parsedNode.getChildren().size() > 0) {
                throw new ParseException("Unexpected attribute(s) " + StringUtil.join(parsedNode.getChildren(), ", ", new StringUtil.StringUtilsFormatter<ParsedNode>() {
                    @Override
                    public String toString(ParsedNode obj) {
                        return "'"+obj.getName()+"'";
                    }
                }) + " for " + objectType.getName(), parsedNode);
            }
            return parsedNode.getValue(null, objectType);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Error parsing '" + parsedNode.getName() + "': cannot convert value '" + parsedNode.getValue() + "' to a " + objectType.getName(), e, parsedNode);
        }
    }
}

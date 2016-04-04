package liquibase.parser.mapping.core;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.ParsedNodeMapping;
import liquibase.util.ObjectUtil;

import java.lang.reflect.Type;

/**
 * Converts parsed nodes to/from non-{@link ExtensibleObject} objects by using {@link ParsedNode#value}
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
        return ObjectUtil.convert(parsedNode.value, objectType);
    }
}

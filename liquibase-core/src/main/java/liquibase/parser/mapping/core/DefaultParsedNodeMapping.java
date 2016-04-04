package liquibase.parser.mapping.core;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * Standard {@link ParsedNodeMapping} to use if nothing else has higher priority.
 * Adds no basic logic beyond what is in {@link AbstractParsedNodeMapping}
 */
public class DefaultParsedNodeMapping extends AbstractParsedNodeMapping {

    public DefaultParsedNodeMapping() {
    }

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (ExtensibleObject.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ExtensibleObject toObject(ParsedNode parsedNode, Class objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        return super.toObject(parsedNode, objectType, containerType, containerAttribute, scope);
    }
}

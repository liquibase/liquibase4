package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * Standard {@link ParsedNodeMapping} to use if nothing else has higher priority.
 */
public class DefaultParsedNodeMapping extends AbstractParsedNodeMapping {

    public DefaultParsedNodeMapping() {
    }

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        return PRIORITY_DEFAULT;
    }
}

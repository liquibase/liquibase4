package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.changelog.ChangeLogEntry;
import liquibase.changelog.ChangeSet;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * {@link ParsedNodeMapping} for {@link ChangeLogEntry} objects.
 */
public class ChangeLogEntryNodeMapping extends AbstractParsedNodeMapping<ChangeLogEntry> {

    public ChangeLogEntryNodeMapping() {
    }

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (ChangeLogEntry.class.isAssignableFrom(objectType) && parsedNode != null && parsedNode.name.equals("changeSet")) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    protected ChangeLogEntry createObject(ParsedNode parsedNode, Class<ChangeLogEntry> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        if (parsedNode.name.equals("changeSet")) {
            return new ChangeSet();
        } else {
            throw new ParseException("Unknown node name: "+parsedNode.name, parsedNode);
        }
    }
}

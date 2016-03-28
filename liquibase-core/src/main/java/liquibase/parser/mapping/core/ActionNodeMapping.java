package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionFactory;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * {@link ParsedNodeMapping} for {@link Action} objects.
 */
public class ActionNodeMapping extends AbstractParsedNodeMapping<Action> {

    public ActionNodeMapping() {
    }

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (Action.class.isAssignableFrom(objectType)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    protected Action createObject(ParsedNode parsedNode, Class<Action> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        if (parsedNode.name.endsWith("Action")) {
            return scope.getSingleton(ActionFactory.class).getAction(parsedNode.name, scope);
        } else {
            throw new ParseException("Unknown node name: "+parsedNode.name);
        }
    }
}

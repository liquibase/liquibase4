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
    protected String getNodeName(Action object, Class containerType, String containerAttribute, Scope scope) {
        return object.getName();
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
        Action action = scope.getSingleton(ActionFactory.class).getAction(parsedNode.name, scope);
        if (action == null) {
            throw new ParseException("Unknown action: " + parsedNode.name);
        }
        return action;
    }
}

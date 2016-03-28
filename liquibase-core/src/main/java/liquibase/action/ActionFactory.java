package liquibase.action;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Factory for finding {@link Action} implementations
 */
public class ActionFactory extends AbstractPluginFactory<Action> {

    protected ActionFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<Action> getPluginClass() {
        return Action.class;
    }

    @Override
    protected int getPriority(Action obj, Scope scope, Object... args) {
        return obj.getPriority((String) args[0], scope);
    }

    /**
     * Returns the best {@link Action} for the given actionName.
     */
    public Action getAction(String actionName, Scope scope) {
        return getPlugin(scope, actionName);
    }
}

package liquibase.action;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Factory for finding {@link Action} implementations
 */
public class ActionFactory extends AbstractPluginFactory<Action> {

    private SortedSet<String> allActionNames;

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
        return (Action) getPlugin(scope, actionName).clone();
    }

    /**
     * Returns the names of all registered {@link Action} implementations.
     */
    public SortedSet<String> getAllActionNames() {
        if (allActionNames == null) {
            allActionNames = new TreeSet<>();
            for (Action action : findAllInstances()) {
                allActionNames.add(action.getName());
            }
        }

        return allActionNames;
    }
}

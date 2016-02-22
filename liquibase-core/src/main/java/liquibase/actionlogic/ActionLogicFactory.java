package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.plugin.Plugin;

/**
 * Factory/registry for looking up the correct ActionLogic implementation. Should normally be accessed using {@link Scope#getSingleton(Class)}, not constructed directly.
 * Even better, normally use {@link ActionExecutor} which is a higher level class.
 */
public class ActionLogicFactory  extends AbstractPluginFactory<ActionLogic> {

    protected ActionLogicFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<ActionLogic> getPluginClass() {
        return ActionLogic.class;
    }

    /**
     * Returns the highest priority {@link liquibase.actionlogic.ActionLogic} implementation that supports the given action/scope pair.
     */
    public ActionLogic getActionLogic(final Action action, final Scope scope) {
        return getPlugin(scope, action);
    }

    @Override
    protected int getPriority(ActionLogic obj, Scope scope, Object... args) {
        Action action = (Action) args[0];
        try {
            if (obj instanceof AbstractActionLogic && !action.getClass().isAssignableFrom(((AbstractActionLogic) obj).getSupportedAction())) {
                return Plugin.PRIORITY_NOT_APPLICABLE;
            }
        } catch (NullPointerException e) {
            throw e;
        }

        return obj.getPriority(action, scope);
    }
}

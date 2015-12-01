package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.servicelocator.AbstractServiceFactory;
import liquibase.servicelocator.Service;

/**
 * Factory/registry for looking up the correct ActionLogic implementation. Should normally be accessed using {@link Scope#getSingleton(Class)}, not constructed directly.
 */
public class ActionLogicFactory  extends AbstractServiceFactory<ActionLogic> {

    /**
     * Constructor is protected because it should be used as a singleton.
     */
    protected ActionLogicFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<ActionLogic> getServiceClass() {
        return ActionLogic.class;
    }

    /**
     * Returns the highest priority {@link liquibase.actionlogic.ActionLogic} implementation that supports the given action/scope pair.
     */
    public ActionLogic getActionLogic(final Action action, final Scope scope) {
        return getService(scope, action);
    }

    @Override
    protected int getPriority(ActionLogic obj, Scope scope, Object... args) {
        Action action = (Action) args[0];
        if (obj instanceof AbstractActionLogic && !action.getClass().isAssignableFrom(((AbstractActionLogic) obj).getSupportedAction())) {
            return Service.PRIORITY_NOT_APPLICABLE;
        }

        return obj.getPriority(action, scope);
    }
}

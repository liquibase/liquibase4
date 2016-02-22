package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.exception.ActionPerformException;
import liquibase.ValidationErrors;
import liquibase.servicelocator.Service;

/**
 * Implementations of this interface contain the logic to handle an {@link liquibase.action.Action} object.
 * Which ActionLogic implementation is used will be based on which returns the highest value from {@link #getPriority(liquibase.action.Action, liquibase.Scope)}.
 * For convenience, consider extending {@link AbstractActionLogic}.
 * If this ActionLogic interacts with an external system, return true from {@link liquibase.actionlogic.ActionLogic#executeInteractsExternally(Action, Scope)}.
 * ActionLogic implementations are used as singletons, so do not class variables.
 */
public interface ActionLogic<ActionType extends Action> extends Service {

    /**
     * Returns the priority for this ActionLogic implementation for the given Action and Scope.
     * If this ActionLogic does not apply, return {@link #PRIORITY_NOT_APPLICABLE}.
     */
    int getPriority(ActionType action, Scope scope);

    /**
     * Validates the given action. Validation can include both errors and warnings.
     */
    ValidationErrors validate(ActionType action, Scope scope);

    /**
     * Check if the action has already been executed against the given scope. Return {@link liquibase.action.ActionStatus.Status#unknown} if it is impossible to test.
     */
    ActionStatus checkStatus(ActionType action, Scope scope);


    /**
     * Returns true if this ActionLogic implementation will interact with an external system (database, server, etc.) for the given action and scope on {@link #execute(Action, Scope)}.
     * If true, execute can be safely called any number of times and it will probably return {@link DelegateResult}.
     * Even if a DelegateResult is returned but external systems are accessed to determine what goes into the DelegateResult, still return true;
     */
    boolean executeInteractsExternally(ActionType action, Scope scope);

    /**
     * Execute the given action.
     */
    ActionResult execute(ActionType action, Scope scope) throws ActionPerformException;

}

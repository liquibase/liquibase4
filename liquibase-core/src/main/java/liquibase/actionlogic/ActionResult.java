package liquibase.actionlogic;

import liquibase.action.Action;

/**
 * Base class for results of {@link ActionLogic#execute(liquibase.action.Action, liquibase.Scope)}.
 * Actual implementation returned will be one of:
 * <ul>
 *     <li>{@link liquibase.actionlogic.ExecuteResult}</li>
 *     <li>{@link liquibase.actionlogic.QueryResult}</li>
 *     <li>{@link liquibase.actionlogic.UpdateResult}</li>
 *     <li>{@link DelegateResult}</li>
 * </ul>
 */
public abstract class ActionResult {

    private Action sourceAction;

    /**
     * Returns the message (if any) associated with this result.
     */
    private String message;

    public ActionResult(Action sourceAction) {
        this.sourceAction = sourceAction;
    }

    public ActionResult(Action sourceAction, String message) {
        this.message = message;
        this.sourceAction = sourceAction;
    }

    /**
     * Returns the message (if any) associated with this result.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The action that generated this result.
     */
    public Action getSourceAction() {
        return sourceAction;
    }

}

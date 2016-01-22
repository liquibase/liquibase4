package liquibase.actionlogic;

import liquibase.AbstractExtensibleObject;
import liquibase.action.Action;
import liquibase.exception.ActionPerformException;

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

    public ActionResult(String message, Action sourceAction) {
        this.message = message;
        this.sourceAction = sourceAction;
    }

    /**
     * Returns the message (if any) associated with this result.
     */
    public String getMessage() {
        return message;
    }

    public Action getSourceAction() {
        return sourceAction;
    }

    /**
     * Implementations contain logic to modify the data in an ActionResult and return a new result.
     * Used to adapt the results of an {@link liquibase.actionlogic.ActionLogic} implementation through another.
     */
    public interface Modifier {

        ActionResult rewrite(ActionResult result) throws ActionPerformException;

    }
}

package liquibase.actionlogic;

import liquibase.action.Action;
import liquibase.util.StringUtils;

/**
 * Result for a generic action which isn't a query, update or rewrite.
 */
public class ExecuteResult extends ActionResult {

    public ExecuteResult(Action sourceAction) {
        super(sourceAction);
    }

    public ExecuteResult(String message, Action sourceAction) {
        super(message, sourceAction);
    }

    @Override
    public String toString() {
        return "Executed: "+ StringUtils.defaultIfEmpty(getMessage(), "No Message");
    }
}

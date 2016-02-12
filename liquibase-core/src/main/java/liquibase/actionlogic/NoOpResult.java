package liquibase.actionlogic;

import liquibase.action.Action;

/**
 * A result that did nothing. If the action did something but returned no values, still use a {@link QueryResult}.
 */
public class NoOpResult extends ActionResult {

    public NoOpResult(Action sourceAction) {
        super(sourceAction);
    }

    public NoOpResult(Action sourceAction, String message) {
        super(sourceAction, message);
    }
}

package liquibase.actionlogic;

import liquibase.action.Action;

public class NoOpResult extends ActionResult {

    public NoOpResult(Action sourceAction) {
        super(sourceAction);
    }

    public NoOpResult(String message, Action sourceAction) {
        super(message, sourceAction);
    }
}

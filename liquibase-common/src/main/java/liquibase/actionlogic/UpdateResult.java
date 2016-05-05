package liquibase.actionlogic;

import liquibase.action.Action;

/**
 * Result of an action that updates existing data.
 */
public class UpdateResult extends ActionResult {

    private long numberAffected;

    public UpdateResult(Action sourceAction, long numberAffected) {
        super(sourceAction);
        this.numberAffected = numberAffected;
    }

    public UpdateResult(Action sourceAction, String message, long numberAffected) {
        super(sourceAction, message);
        this.numberAffected = numberAffected;
    }

    /**
     * Returns the number of items (such as rows) updated.
     */
    public long getNumberAffected() {
        return numberAffected;
    }
}

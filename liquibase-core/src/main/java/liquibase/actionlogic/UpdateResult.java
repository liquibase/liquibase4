package liquibase.actionlogic;

import liquibase.action.Action;

/**
 * Result of an action that updates existing data.
 */
public class UpdateResult extends ActionResult {

    private long numberAffected;

    public UpdateResult(long numberAffected, Action sourceAction) {
        super(sourceAction);
        this.numberAffected = numberAffected;
    }

    public UpdateResult(long numberAffected, String message, Action sourceAction) {
        super(message, sourceAction);
        this.numberAffected = numberAffected;
    }

    /**
     * Returns the number of items (such as rows) updated.
     */
    public long getNumberAffected() {
        return numberAffected;
    }
}

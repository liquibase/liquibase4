package liquibase.actionlogic;

import liquibase.action.Action;
import liquibase.exception.UnexpectedLiquibaseException;

import java.util.*;

/**
 * Result of an action that returns more than one {@link liquibase.actionlogic.ActionResult}.
 */
public class CompoundResult extends ActionResult {

    private List<ActionResult> results = new ArrayList<>();

    public CompoundResult(Action sourceAction) {
        super(sourceAction);
    }

    /**
     * Creates a new CompoundResult of the given Action/ActionResult pairs.
     */
    public CompoundResult(List<ActionResult> results, Action sourceAction) {
        super(sourceAction);
        if (results == null || results.size() == 0) {
            throw new UnexpectedLiquibaseException("Null or empty results passed to a CompoundResult");
        }

        this.results = results;

    }

    /**
     * Returns the results stored in this CompoundResult, flattened to a single list.
     * They are returned in the order the actions were executed.
     * The returned list is unmodifiable.
     */
    public List<ActionResult> getFlatResults() {
        List<ActionResult> flatResults = new ArrayList<>();
        for (ActionResult result : results) {
            if (result instanceof CompoundResult) {
                flatResults.addAll(((CompoundResult) result).getFlatResults());
            } else {
                flatResults.add(result);
            }
        }
        return Collections.unmodifiableList(flatResults);
    }

    /**
     * Returns the results stored in this CompoundResult, without flattening.
     */
    public List<ActionResult> getNestedResults() {
        return Collections.unmodifiableList(results);
    }

    /**
     * Returns the results stored in this CompoundResult, keyed by the action that generated the result.
     * The returned Map iterator order preserves the original execution order.
     * The returned map is unmodifiable.
     */
    public ActionResult getResultsByAction(Action sourceAction) {
        for (ActionResult result : results) {
            if (result.getSourceAction().equals(sourceAction)) {
                return result;
            }
        }
        return null;
    }

    public void addResult(ActionResult result) {
        if (result != null) {
            this.results.add(result);
        }
    }
}

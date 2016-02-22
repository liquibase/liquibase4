package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Result of {@link ActionExecutor#execute(Action, Scope)} that returns more than one {@link liquibase.actionlogic.ActionResult}.
 * You can get a compound result if, for example, it takes multiple sub-steps to execute a higher-level action.
 */
public class CompoundResult extends ActionResult {

    private List<ActionResult> results = new ArrayList<>();

    public CompoundResult(Action sourceAction, ActionResult... results) {
        this(sourceAction, new ArrayList<>(Arrays.asList(CollectionUtil.createIfNull(results))));
    }

    public CompoundResult(Action sourceAction, List<ActionResult> results) {
        super(sourceAction);

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
     * Adds the given ActionResult to this CompoundResult
     */
    public CompoundResult addResult(ActionResult result) {
        if (result != null) {
            this.results.add(result);
        }
        return this;
    }
}

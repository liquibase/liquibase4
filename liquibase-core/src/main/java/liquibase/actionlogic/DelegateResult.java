package liquibase.actionlogic;

import liquibase.action.Action;
import liquibase.exception.ActionPerformException;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ActionLogic result which rewrites an action in the form of one or more new actions.
 * A {@link Modifier} can be us included which will adapt the results of the rewritten action.
 */
public class DelegateResult extends ActionResult {

    private List<Action> actions = new ArrayList<>();

    private Modifier modifier;

    public DelegateResult(Action sourceAction, Modifier modifier, Action... actions) {
        super(sourceAction);
        for (Action action : CollectionUtil.createIfNull(actions)) {
            if (action != null) {
                this.actions.add(action);
            }
        }
        this.modifier = modifier;
    }

    /**
     * Returns an unmodifyable list of the actions to delegate to.
     */
    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    /**
     * Adds action(s) to this DelegateResult
     */
    public DelegateResult addActions(Action... actions) {
        this.actions.addAll(Arrays.asList(CollectionUtil.createIfNull(actions)));

        return this;
    }

    public Modifier getModifier() {
        return modifier;
    }

    /**
     * Implementations contain logic to modify the data in an ActionResult and return a new result.
     * Used to adapt the results of an {@link ActionLogic} implementation through another.
     */
    public interface Modifier {

        /**
         * Convert the result of the delegated action into the format returned by a {@link DelegateResult}
         */
        ActionResult rewrite(ActionResult result) throws ActionPerformException;

    }
}

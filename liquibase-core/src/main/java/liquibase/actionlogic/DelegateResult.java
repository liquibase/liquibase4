package liquibase.actionlogic;

import liquibase.action.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ActionLogic result which rewrites an action in the form of one or more new actions.
 * A {@link liquibase.actionlogic.ActionResult.Modifier} can be us included which will adapt the results of the rewritten action.
 */
public class DelegateResult extends ActionResult {

    private List<Action> actions = new ArrayList<>();

    private ActionResult.Modifier modifier;

    public DelegateResult(Action sourceAction, ActionResult.Modifier modifier, Action... actions) {
        super(sourceAction);
        if (actions != null) {
            for (Action action : actions) {
                if (action != null) {
                    this.actions.add(action);
                }
            }
        }
        if (modifier != null) {
            this.modifier = modifier;
        }
    }

    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public DelegateResult addActions(Action... actions) {
        if (actions != null) {
            this.actions.addAll(Arrays.asList(actions));
        }

        return this;
    }

    public ActionResult.Modifier getModifier() {
        return modifier;
    }
}

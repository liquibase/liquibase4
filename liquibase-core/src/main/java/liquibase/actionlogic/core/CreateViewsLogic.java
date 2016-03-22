package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateViewsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Sequence;
import liquibase.item.core.View;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateViewsLogic extends AbstractActionLogic<CreateViewsAction> {

    public enum Clauses {
        definition
    }

    @Override
    protected Class<CreateViewsAction> getSupportedAction() {
        return CreateViewsAction.class;
    }

    @Override
    public ValidationErrors validate(CreateViewsAction action, final Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("views",
                        "views.name", "views.definition");
    }

    @Override
    public ActionResult execute(CreateViewsAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (View view : action.views) {
            actions.addAll(Arrays.asList(new ExecuteSqlAction(generateSql(view, action, scope))));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(View view, CreateViewsAction action, final Scope scope) {
        return new StringClauses()
                .append("CREATE")
                .append("VIEW")
                .append(scope.getDatabase().quoteObjectName(view.toReference(), scope))
                .append("AS")
                .append(Clauses.definition, view.definition);
    }

    @Override
    public ActionStatus checkStatus(CreateViewsAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (View actionView : action.views) {
                View snapshotView = scope.getSingleton(SnapshotFactory.class).snapshot(actionView.toReference(), scope);
                if (snapshotView == null) {
                    result.assertApplied(false, "View '" + actionView.toReference() + "' not found");
                } else {
                    result.nothingToCheck();
                }
            }
            return result;
        } catch (Throwable e) {
            return result.unknown(e);
        }

    }
}

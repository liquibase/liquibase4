package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropSequencesAction;
import liquibase.action.core.DropViewsAction;
import liquibase.action.core.DropViewsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.RelationReference;
import liquibase.item.core.SequenceReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropViewsLogic extends AbstractActionLogic<DropViewsAction> {

    @Override
    protected Class<DropViewsAction> getSupportedAction() {
        return DropViewsAction.class;
    }

    @Override
    public ValidationErrors validate(DropViewsAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("views", "views.name");
    }

    @Override
    public ActionStatus checkStatus(DropViewsAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (RelationReference view : action.views) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(view, scope) == null, "View " + view + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
        return status;
    }


    @Override
    public ActionResult execute(DropViewsAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (RelationReference view : action.views) {
            actions.add(new ExecuteSqlAction(generateSql(view, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(RelationReference view, DropViewsAction action, Scope scope) {
        return new StringClauses()
                .append("DROP VIEW")
                .append(scope.getDatabase().quoteObjectName(view, scope));
    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropUniqueConstraintsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.UniqueConstraintReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropUniqueConstraintsLogic extends AbstractActionLogic<DropUniqueConstraintsAction> {

    @Override
    protected Class<DropUniqueConstraintsAction> getSupportedAction() {
        return DropUniqueConstraintsAction.class;
    }

    @Override
    public ValidationErrors validate(DropUniqueConstraintsAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("constraints", "constraints.container", "constraints.name");
    }


    @Override
    public ActionResult execute(DropUniqueConstraintsAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (UniqueConstraintReference uq : action.constraints) {
            actions.add(generateAction(uq, action, scope));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action generateAction(UniqueConstraintReference uq, DropUniqueConstraintsAction action, Scope scope) {
        return new AlterTableAction(uq.getRelation(), new StringClauses().append("DROP").append("CONSTRAINT").append(scope.getDatabase().quoteObjectName(uq, scope)));
    }

    @Override
    public ActionStatus checkStatus(DropUniqueConstraintsAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (UniqueConstraintReference uq : action.constraints) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(uq, scope) == null, "UniqueConstraint " + uq + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }

        return status;

    }
}

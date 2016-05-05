package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropPrimaryKeysAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.PrimaryKeyReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropPrimaryKeysLogic extends AbstractActionLogic<DropPrimaryKeysAction> {

    @Override
    protected Class<DropPrimaryKeysAction> getSupportedAction() {
        return DropPrimaryKeysAction.class;
    }

    @Override
    public ValidationErrors validate(DropPrimaryKeysAction action, Scope scope) {
        final boolean supportsNames = scope.getDatabase().supports(Database.Feature.NAMED_PRIMARY_KEYS, scope);
        return super.validate(action, scope)
                .checkRequiredFields("primaryKeys", "primaryKeys.container")
                .checkField("primaryKeys.name", new ValidationErrors.FieldCheck<String>() {
                    @Override
                    public String check(String obj) {
                        if (obj != null && !supportsNames) {
                            return "named primary keys are not supported";
                        } else {
                            return null;
                        }
                    }
                });
    }


    @Override
    public ActionResult execute(DropPrimaryKeysAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (PrimaryKeyReference pk : action.primaryKeys) {
            actions.add(generateAction(pk, action, scope));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action generateAction(PrimaryKeyReference pkRef, DropPrimaryKeysAction action, Scope scope) {
        return new AlterTableAction(pkRef.getRelation(), new StringClauses().append("DROP").append("PRIMARY KEY"));
    }

    @Override
    public ActionStatus checkStatus(DropPrimaryKeysAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (PrimaryKeyReference pk : action.primaryKeys) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(pk, scope) == null, "PrimaryKey " + pk + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }

        return status;

    }
}

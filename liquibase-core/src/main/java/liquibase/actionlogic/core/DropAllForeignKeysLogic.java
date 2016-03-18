package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.DropAllForeignKeysAction;
import liquibase.action.core.DropForeignKeysAction;
import liquibase.actionlogic.*;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.ForeignKey;
import liquibase.snapshot.SnapshotFactory;

import java.util.Collection;

public class DropAllForeignKeysLogic extends AbstractActionLogic<DropAllForeignKeysAction> {

    @Override
    protected Class<? extends DropAllForeignKeysAction> getSupportedAction() {
        return DropAllForeignKeysAction.class;
    }

    @Override
    public ValidationErrors validate(DropAllForeignKeysAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("table", "table.name");
    }

    @Override
    public ActionStatus checkStatus(DropAllForeignKeysAction action, Scope scope) {
        try {
            return new ActionStatus()
                    .assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshotAll(ForeignKey.class, action.table, scope).size() == 0, "Foreign keys were found on " + action.table);
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(DropAllForeignKeysAction action, Scope scope) throws ActionPerformException {

        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);
        Collection<ForeignKey> foreignKeys = scope.getSingleton(SnapshotFactory.class).snapshotAll(ForeignKey.class, action.table, scope);

        if (foreignKeys.size() == 0) {
            return new NoOpResult(action);
        } else {
            CompoundResult result =  new CompoundResult(action);
            for (ForeignKey fk : foreignKeys) {
                result.addResult(actionExecutor.execute(new DropForeignKeysAction(fk.toReference()), scope));
            }
            return result;
        }
    }

    @Override
    public boolean executeInteractsExternally(DropAllForeignKeysAction action, Scope scope) {
        return true;
    }
}

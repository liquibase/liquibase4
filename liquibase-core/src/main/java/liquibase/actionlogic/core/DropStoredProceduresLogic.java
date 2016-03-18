package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropStoredProceduresAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.StoredDatabaseLogicReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropStoredProceduresLogic extends AbstractActionLogic<DropStoredProceduresAction> {

    @Override
    protected Class<DropStoredProceduresAction> getSupportedAction() {
        return DropStoredProceduresAction.class;
    }

    @Override
    public ValidationErrors validate(DropStoredProceduresAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("procedures", "procedures.name");
    }

    @Override
    public ActionStatus checkStatus(DropStoredProceduresAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (StoredDatabaseLogicReference storedProcedure : action.procedures) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(storedProcedure, scope) == null, "Stored procedure " + storedProcedure + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
        return status;
    }

    @Override
    public ActionResult execute(DropStoredProceduresAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (StoredDatabaseLogicReference storedProcedure : action.procedures) {
            actions.add(new ExecuteSqlAction(generateSql(storedProcedure, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(StoredDatabaseLogicReference storedProcedure, DropStoredProceduresAction action, Scope scope) {
        return new StringClauses()
                .append("DROP")
                .append("PROCEDURE")
                .append(scope.getDatabase().quoteObjectName(storedProcedure, scope));
    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateStoredProceduresAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.StoredProcedure;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateStoredProceduresLogic extends AbstractActionLogic<CreateStoredProceduresAction> {

    public enum Clauses {
        body
    }

    @Override
    protected Class<? extends CreateStoredProceduresAction> getSupportedAction() {
        return CreateStoredProceduresAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supports(StoredProcedure.class, scope);
    }

    @Override
    public ValidationErrors validate(CreateStoredProceduresAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("procedures")
                .checkRequiredFields("procedures.body")
                .checkUnsupportedFields("replaceIfExists"); //don't support replaceIfExists by default
    }

    @Override
    public ActionStatus checkStatus(CreateStoredProceduresAction action, Scope scope) {
        try {
            ActionStatus status = new ActionStatus();
            for (StoredProcedure proc : action.procedures) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).has(proc.toReference(), scope), "Cannot find procedure " + proc.toReference());
            }
            return status;
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(CreateStoredProceduresAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (StoredProcedure procedure : action.procedures) {
            actions.addAll(Arrays.asList(execute(procedure, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action execute(StoredProcedure procedure, CreateStoredProceduresAction action, Scope scope) {
        return new ExecuteSqlAction(generateSql(procedure, action, scope));
    }

    protected StringClauses generateSql(StoredProcedure procedure, CreateStoredProceduresAction action, final Scope scope) {
        return new StringClauses().append(Clauses.body, procedure.body);
    }
}
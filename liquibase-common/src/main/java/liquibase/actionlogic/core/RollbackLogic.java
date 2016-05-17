package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.RollbackAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;

public class RollbackLogic extends AbstractActionLogic<RollbackAction> {

    @Override
    protected Class<? extends RollbackAction> getSupportedAction() {
        return RollbackAction.class;
    }

    @Override
    public ActionResult execute(RollbackAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new ExecuteSqlAction("ROLLBACK"));
    }

    @Override
    public ActionStatus checkStatus(RollbackAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }
}

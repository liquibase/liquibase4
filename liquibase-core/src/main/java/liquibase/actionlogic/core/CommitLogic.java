package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CommitAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.ExecuteResult;
import liquibase.exception.ActionPerformException;
import liquibase.exception.DatabaseException;

public class CommitLogic extends AbstractActionLogic<CommitAction> {

    @Override
    protected Class<? extends CommitAction> getSupportedAction() {
        return CommitAction.class;
    }

    @Override
    public ActionResult execute(CommitAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new ExecuteSqlAction("COMMIT"));
    }

    @Override
    public ActionStatus checkStatus(CommitAction action, Scope scope) {
        return new ActionStatus().assertCorrect(true, "Nothing to check");
    }
}

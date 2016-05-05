package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.ThrowExceptionAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.exception.ActionPerformException;

public class ThrowExceptionLogic extends AbstractActionLogic<ThrowExceptionAction> {

    @Override
    protected Class<? extends ThrowExceptionAction> getSupportedAction() {
        return ThrowExceptionAction.class;
    }

    @Override
    public boolean executeInteractsExternally(ThrowExceptionAction action, Scope scope) {
        return true;
    }

    @Override
    public ValidationErrors validate(ThrowExceptionAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("exception");
    }

    @Override
    public ActionStatus checkStatus(ThrowExceptionAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }

    @Override
    public ActionResult execute(ThrowExceptionAction action, Scope scope) throws ActionPerformException {
        throw new ActionPerformException(action.exception);
    }
}

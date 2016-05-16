package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.CommentAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.NoOpResult;
import liquibase.exception.ActionPerformException;

/**
 * Default logic for {@link CommentAction} is just a {@link NoOpResult}.
 */
public class CommentLogic extends AbstractActionLogic<CommentAction> {

    @Override
    protected Class<? extends CommentAction> getSupportedAction() {
        return CommentAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return true;
    }

    @Override
    public ActionResult execute(CommentAction action, Scope scope) throws ActionPerformException {
        return new NoOpResult(action);
    }
}

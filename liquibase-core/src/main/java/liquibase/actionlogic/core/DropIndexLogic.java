package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.core.Index;

public class DropIndexLogic extends AbstractActionLogic<DropIndexAction> {

    @Override
    protected Class<DropIndexAction> getSupportedAction() {
        return DropIndexAction.class;
    }

    @Override
    public ValidationErrors validate(DropIndexAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("index", "index.name");
    }


    @Override
    public ActionResult execute(DropIndexAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        return new DelegateResult(action, null, new ExecuteSqlAction("DROP INDEX " + database.quoteObjectName(action.index.name, Index.class, scope)));
    }

}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;

public class AlterTableLogic extends AbstractActionLogic<AlterTableAction> {

    @Override
    protected Class<AlterTableAction> getSupportedAction() {
        return AlterTableAction.class;
    }

    @Override
    public ValidationErrors validate(AlterTableAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("table", "newDefinition");
    }

    @Override
    public ActionStatus checkStatus(AlterTableAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        result.nothingToCheck();

        return result;
    }

    @Override
    public ActionResult execute(AlterTableAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        return new DelegateResult(action, null, new ExecuteSqlAction("ALTER TABLE "
                + database.quoteObjectName(action.table, scope)
                + " "
                + action.newDefinition.toString().trim()));
    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;

public class AlterTableLogic extends AbstractActionLogic<AlterTableAction> {

    @Override
    protected Class<AlterTableAction> getSupportedAction() {
        return AlterTableAction.class;
    }

    @Override
    public ValidationErrors validate(AlterTableAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields(action, "table", "newDefinition");
    }

    @Override
    public ActionResult execute(AlterTableAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        return new DelegateResult(action, null, new ExecuteSqlAction("ALTER TABLE "
                + database.escapeObjectName(action.table)
                + " "
                + action.newDefinition.toString().trim()));
    }
}

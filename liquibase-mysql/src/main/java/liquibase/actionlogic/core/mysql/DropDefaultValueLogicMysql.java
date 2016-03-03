package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.DropDefaultValueAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.AlterColumnLogic;
import liquibase.actionlogic.core.DropDefaultValueLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.util.StringClauses;

public class DropDefaultValueLogicMysql extends DropDefaultValueLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ActionResult execute(DropDefaultValueAction action, Scope scope) throws ActionPerformException {
        DelegateResult result = (DelegateResult) super.execute(action, scope);
        for (Action delegateAction : result.getActions()) {
            if (delegateAction instanceof AlterColumnAction) {
                ((AlterColumnAction) delegateAction).alterColumnKeyword = "ALTER COLUMN";
            }
        }
        return result;
    }
}

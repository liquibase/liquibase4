package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.AlterDefaultValueAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.AlterDefaultValueLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;

public class AlterDefaultValueLogicMysql extends AlterDefaultValueLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ActionResult execute(AlterDefaultValueAction action, Scope scope) throws ActionPerformException {
        DelegateResult result = (DelegateResult) super.execute(action, scope);
        for (Action delegateAction : result.getActions()) {
            if (delegateAction instanceof AlterColumnAction) {
                ((AlterColumnAction) delegateAction).alterColumnKeyword = "ALTER COLUMN";
            }
        }
        return result;
    }
}

package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.DropIndexLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;

public class DropIndexLogicMysql extends DropIndexLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(DropIndexAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("table");
    }

    @Override
    public ActionResult execute(DropIndexAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();

        return new DelegateResult(action, null, new ExecuteSqlAction(
                "DROP INDEX "
                        + database.escapeObjectName(action.indexName)
                        + " ON "
                        + database.escapeObjectName(action.tableName)));

    }
}

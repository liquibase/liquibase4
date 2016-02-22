package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.DropIndexLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.structure.core.Index;

public class DropIndexLogicMysql extends DropIndexLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(DropIndexAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("index.container");
    }

    @Override
    public ActionResult execute(DropIndexAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();

        return new DelegateResult(action, null, new ExecuteSqlAction(
                "DROP INDEX "
                        + database.quoteObjectName(action.index.name, Index.class, scope)
                        + " ON "
                        + database.quoteObjectName(action.index.getTable(), scope)));

    }
}

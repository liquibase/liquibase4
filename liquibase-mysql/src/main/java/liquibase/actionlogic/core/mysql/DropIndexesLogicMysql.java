package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexesAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.DropIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Index;
import liquibase.item.core.IndexReference;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropIndexesLogicMysql extends DropIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(DropIndexesAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("indexes.container");
    }

    @Override
    protected StringClauses generateSql(IndexReference index, DropIndexesAction action, Scope scope) {
        Database database = scope.getDatabase();

        return new StringClauses()
                .append("DROP INDEX")
                .append(database.quoteObjectName(index.name, Index.class, scope))
                .append("ON")
                .append(database.quoteObjectName(index.getRelation(), scope));
    }
}

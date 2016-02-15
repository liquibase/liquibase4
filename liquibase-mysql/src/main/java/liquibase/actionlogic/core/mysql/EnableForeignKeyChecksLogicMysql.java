package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.EnableForeignKeyChecksAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.util.StringClauses;

public class EnableForeignKeyChecksLogicMysql extends AbstractSqlBuilderLogic<EnableForeignKeyChecksAction> {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Class<? extends EnableForeignKeyChecksAction> getSupportedAction() {
        return EnableForeignKeyChecksAction.class;
    }

    @Override
    protected StringClauses generateSql(EnableForeignKeyChecksAction action, Scope scope) {
        return new StringClauses().append("SET FOREIGN_KEY_CHECKS=1");
    }
}

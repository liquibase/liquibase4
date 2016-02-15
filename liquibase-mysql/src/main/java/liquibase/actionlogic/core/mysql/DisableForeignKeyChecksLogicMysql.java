package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.DisableForeignKeyChecksAction;
import liquibase.action.core.EnableForeignKeyChecksAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.util.StringClauses;

public class DisableForeignKeyChecksLogicMysql extends AbstractSqlBuilderLogic<DisableForeignKeyChecksAction> {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Class<? extends DisableForeignKeyChecksAction> getSupportedAction() {
        return DisableForeignKeyChecksAction.class;
    }

    @Override
    protected StringClauses generateSql(DisableForeignKeyChecksAction action, Scope scope) {
        return new StringClauses().append("SET FOREIGN_KEY_CHECKS=0");
    }
}

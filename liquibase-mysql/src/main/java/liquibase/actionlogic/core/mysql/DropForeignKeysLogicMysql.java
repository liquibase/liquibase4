package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.DropForeignKeysAction;
import liquibase.actionlogic.core.DropForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.ForeignKeyReference;
import liquibase.util.StringClauses;

public class DropForeignKeysLogicMysql extends DropForeignKeysLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected StringClauses generateSql(ForeignKeyReference foreignKey, DropForeignKeysAction action, Scope scope) {
        return super.generateSql(foreignKey, action, scope)
                .replace("DROP CONSTRAINT", "DROP FOREIGN KEY");
    }
}

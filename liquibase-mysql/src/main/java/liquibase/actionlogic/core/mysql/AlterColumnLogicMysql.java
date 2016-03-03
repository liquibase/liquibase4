package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.AlterColumnAction;
import liquibase.actionlogic.core.AlterColumnLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.util.StringClauses;

public class AlterColumnLogicMysql extends AlterColumnLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }


    @Override
    protected StringClauses getAlterColumnClauses(AlterColumnAction action, Scope scope) {
        StringClauses alterColumnClauses = super.getAlterColumnClauses(action, scope);
        if (action.alterColumnKeyword == null) {
            alterColumnClauses.replace("ALTER COLUMN", "MODIFY");
        }
        return alterColumnClauses;
    }
}

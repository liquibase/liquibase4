package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.InsertDataAction;
import liquibase.actionlogic.core.InsertDataLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.RowData;
import liquibase.util.StringClauses;

public class InsertDataLogicMysql extends InsertDataLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected StringClauses generateMergeSql(RowData row, InsertDataAction action, Scope scope) {
        String updateColumns = super.generateMergeSql(row, action, scope).get(Clauses.updateColumns);

        return super.generateInsertSql(row, action, scope)
                .append("ON DUPLICATE KEY UPDATE")
                .append(updateColumns);
    }
}

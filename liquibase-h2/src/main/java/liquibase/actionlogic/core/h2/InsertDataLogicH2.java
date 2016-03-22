package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.core.InsertDataAction;
import liquibase.actionlogic.core.InsertDataLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.item.core.Column;
import liquibase.item.core.RowData;
import liquibase.util.StringClauses;

public class InsertDataLogicH2 extends InsertDataLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected StringClauses generateMergeSql(RowData row, InsertDataAction action, Scope scope) {
        StringClauses keysClause = new StringClauses("(", ", ", ")");
        for (String column : action.columnsForUpdateCheck) {
            keysClause.append(scope.getDatabase().quoteObjectName(column, Column.class, scope));
        }
        return generateInsertSql(row, action, scope)
                .replace("INSERT", "MERGE")
                .insertBefore("VALUES", "KEY")
                .insertAfter("KEY", "keysColumns", keysClause);
    }
}

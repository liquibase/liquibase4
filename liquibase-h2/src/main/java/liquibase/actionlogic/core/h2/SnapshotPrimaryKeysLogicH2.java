package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.util.StringClauses;

public class SnapshotPrimaryKeysLogicH2 extends SnapshotPrimaryKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String primaryKeyName, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses whereClauses = new StringClauses(" AND ");
        if (jdbcCatalogName != null) {
            whereClauses.append("TABLE_CATALOG='"+database.escapeString(jdbcCatalogName)+"'");
        }
        if (jdbcSchemaName != null) {
            whereClauses.append("TABLE_SCHEMA='"+database.escapeString(jdbcSchemaName )+"'");
        }
        if (tableName != null) {
            whereClauses.append("TABLE_NAME='"+database.escapeString(tableName )+"'");
        }
        whereClauses.append("PRIMARY_KEY=TRUE");

        StringClauses sql  = new StringClauses(" ")
                .append("SELECT TABLE_CATALOG TABLE_CAT, TABLE_SCHEMA TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION KEY_SEQ, IFNULL(CONSTRAINT_NAME, INDEX_NAME) PK_NAME")
                .append("FROM INFORMATION_SCHEMA.INDEXES")
                .append("WHERE")
                .append(whereClauses.toString())
                .append("ORDER BY COLUMN_NAME");

        return new QuerySqlAction(sql);
    }
}

package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.actionlogic.core.SnapshotIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.util.StringClauses;

public class SnapshotIndexesLogicMysql extends SnapshotIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String indexName, boolean unique, boolean approximate, Scope scope) {

        Database database = scope.getDatabase();
        StringClauses whereClauses = new StringClauses(" AND ");
        if (jdbcCatalogName != null) {
            whereClauses.append("TABLE_SCHEMA='" + database.escapeString(jdbcCatalogName)+"'");
        }
        if (tableName != null) {
            whereClauses.append("TABLE_NAME='" + database.escapeString(tableName)+"'");
        }
        if (indexName != null) {
            whereClauses.append("INDEX_NAME='" + database.escapeString(indexName)+"'");
        }
        if (unique) {
            whereClauses.append("NON_UNIQUE=0");
        }

        StringClauses sql = new StringClauses().append("SELECT")
                .append("TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, NON_UNIQUE, TABLE_SCHEMA AS INDEX_QUALIFIER, INDEX_NAME,3 AS TYPE, SEQ_IN_INDEX AS ORDINAL_POSITION, COLUMN_NAME, 'A' AS ASC_OR_DESC, CARDINALITY, NULL AS PAGES, NULL AS FILTER_CONDITION")
                .append("FROM INFORMATION_SCHEMA.STATISTICS");
        if (!whereClauses.isEmpty()) {
            sql.append("WHERE").append(whereClauses.toString());
        }

        sql.append("ORDER BY NON_UNIQUE, INDEX_NAME, SEQ_IN_INDEX");

        return new QuerySqlAction(sql);
    }


}

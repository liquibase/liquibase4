package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.actionlogic.core.SnapshotIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.util.StringClauses;

public class SnapshotIndexesLogicH2 extends SnapshotIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String indexName, boolean unique, boolean approximate, Scope scope) {

        Database database = scope.getDatabase();
        StringClauses whereClauses = new StringClauses(" AND ");

        if (jdbcCatalogName != null) {
            whereClauses.append("TABLE_CATALOG='" + database.escapeString(jdbcCatalogName)+"'");
        }
        if (jdbcSchemaName != null) {
            whereClauses.append("TABLE_SCHEMA='" + database.escapeString(jdbcSchemaName)+"'");
        }
        if (tableName != null) {
            whereClauses.append("TABLE_NAME='" + database.escapeString(tableName)+"'");
        }
        if (indexName != null) {
            whereClauses.append("INDEX_NAME='" + database.escapeString(indexName)+"'");
        }
        if (unique) {
            whereClauses.append("NON_UNIQUE=FALSE");
        }

        StringClauses sql = new StringClauses().append("SELECT")
                .append("TABLE_CATALOG TABLE_CAT, TABLE_SCHEMA TABLE_SCHEM, TABLE_NAME, NON_UNIQUE, TABLE_CATALOG INDEX_QUALIFIER, INDEX_NAME, INDEX_TYPE TYPE, ORDINAL_POSITION, COLUMN_NAME, ASC_OR_DESC, CARDINALITY, PAGES, FILTER_CONDITION, SORT_TYPE")
                .append("FROM INFORMATION_SCHEMA.INDEXES");
        if (!whereClauses.isEmpty()) {
            sql.append("WHERE").append(whereClauses.toString());
        }

        sql.append("ORDER BY NON_UNIQUE, TYPE, TABLE_SCHEM, INDEX_NAME, ORDINAL_POSITION");

        return new QuerySqlAction(sql);
    }


}

package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.ItemReference;
import liquibase.item.core.PrimaryKey;
import liquibase.util.StringClauses;

public class SnapshotPrimaryKeysLogicMysql extends SnapshotPrimaryKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(SnapshotItemsAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);
        for (ItemReference relatedTo : action.relatedTo) {
            if (relatedTo.instanceOf(PrimaryKey.class) && relatedTo.name != null && !relatedTo.name.equalsIgnoreCase("primary")) {
                errors.addError(scope.getDatabase().getShortName()+" does not support primary key names");
                break;
            }
        }
        return errors;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String primaryKeyName, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses whereClauses = new StringClauses(" AND ");
        whereClauses.append("INDEX_NAME='PRIMARY'");
        if (jdbcCatalogName != null) {
            whereClauses.append("TABLE_SCHEMA=" + database.quoteString(jdbcCatalogName, scope));
        }
        if (tableName != null) {
            whereClauses.append("TABLE_NAME=" + database.quoteString(tableName, scope));
        }


        return new QuerySqlAction(new StringClauses().append("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, SEQ_IN_INDEX AS KEY_SEQ, 'PRIMARY' AS PK_NAME")
                .append("FROM INFORMATION_SCHEMA.STATISTICS")
                .append("WHERE").append(whereClauses.toString())
                .append("ORDER BY TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX"));
    }
}

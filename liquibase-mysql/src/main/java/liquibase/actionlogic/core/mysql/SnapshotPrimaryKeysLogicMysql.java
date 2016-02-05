package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogicJdbc;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.PrimaryKey;
import liquibase.util.StringClauses;

public class SnapshotPrimaryKeysLogicMysql extends SnapshotPrimaryKeysLogicJdbc {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(Action action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
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
            whereClauses.append("TABLE_SCHEMA='" + database.escapeString(jdbcCatalogName)+"'");
        }
        if (tableName != null) {
            whereClauses.append("TABLE_NAME='" + database.escapeString(tableName)+"'");
        }


        return new QuerySqlAction(new StringClauses().append("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, SEQ_IN_INDEX AS KEY_SEQ, 'PRIMARY' AS PK_NAME")
                .append("FROM INFORMATION_SCHEMA.STATISTICS")
                .append("WHERE").append(whereClauses.toString())
                .append("ORDER BY TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX"));
    }
}

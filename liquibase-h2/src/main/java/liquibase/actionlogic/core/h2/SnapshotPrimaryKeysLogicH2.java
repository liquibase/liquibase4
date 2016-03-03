package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Table;

public class SnapshotPrimaryKeysLogicH2 extends SnapshotPrimaryKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String primaryKeyName, Scope scope) {
        Database database = scope.getDatabase();

        SelectDataAction sql  = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA","INDEXES"),
                new SelectDataAction.SelectedColumn(null, "TABLE_CATALOG", "TABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "TABLE_SCHEMA","TABLE_SCHEM"),
                new SelectDataAction.SelectedColumn("TABLE_NAME"),
                new SelectDataAction.SelectedColumn("COLUMN_NAME"),
                new SelectDataAction.SelectedColumn(null, "ORDINAL_POSITION","KEY_SEQ"),
                new SelectDataAction.SelectedColumn(null, "IFNULL(CONSTRAINT_NAME, INDEX_NAME)", "PK_NAME", true)
        );
        if (jdbcCatalogName != null) {
            sql.addWhere("TABLE_CATALOG=" + database.quoteString(jdbcCatalogName, scope));
        }
        if (jdbcSchemaName != null) {
            sql.addWhere("TABLE_SCHEMA=" + database.quoteString(jdbcSchemaName, scope));
        }
        if (tableName != null) {
            sql.addWhere("TABLE_NAME=" + database.quoteString(tableName, scope));
        }
        sql.addWhere("PRIMARY_KEY=TRUE");

        sql.addOrder(new SelectDataAction.OrderedByColumn("COLUMN_NAME"));

        return sql;
    }
}

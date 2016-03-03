package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.core.SnapshotIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Table;

public class SnapshotIndexesLogicMysql extends SnapshotIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String indexName, boolean unique, boolean approximate, Scope scope) {

        Database database = scope.getDatabase();

        SelectDataAction sql = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "STATISTICS"),
                new SelectDataAction.SelectedColumn(null, "TABLE_SCHEMA", "TABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "NULL", "TABLE_SCHEM", true),
                new SelectDataAction.SelectedColumn("TABLE_NAME"),
                new SelectDataAction.SelectedColumn("NON_UNIQUE"),
                new SelectDataAction.SelectedColumn(null, "TABLE_SCHEMA", "INDEX_QUALIFIER"),
                new SelectDataAction.SelectedColumn("INDEX_NAME"),
                new SelectDataAction.SelectedColumn(null, "3", "TYPE", true),
                new SelectDataAction.SelectedColumn(null, "SEQ_IN_INDEX", "ORDINAL_POSITION"),
                new SelectDataAction.SelectedColumn("COLUMN_NAME"),
                new SelectDataAction.SelectedColumn(null, "'A'", "ASC_OR_DESC", true),
                new SelectDataAction.SelectedColumn("CARDINALITY"),
                new SelectDataAction.SelectedColumn(null, "NULL", "PAGES", true),
                new SelectDataAction.SelectedColumn(null, "NULL", "FILTER_CONDITION", true)
        );
        if (jdbcCatalogName != null) {
            sql.addWhere("TABLE_SCHEMA=" + database.quoteString(jdbcCatalogName, scope));
        }
        if (tableName != null) {
            sql.addWhere("TABLE_NAME=" + database.quoteString(tableName, scope));
        }
        if (indexName != null) {
            sql.addWhere("INDEX_NAME=" + database.quoteString(indexName, scope));
        }
        if (unique) {
            sql.addWhere("NON_UNIQUE=0");
        }

        sql.addOrder(
                new SelectDataAction.OrderedByColumn("NON_UNIQUE"),
                new SelectDataAction.OrderedByColumn("INDEX_NAME"),
                new SelectDataAction.OrderedByColumn("SEQ_IN_INDEX"));

        return sql;
    }


}

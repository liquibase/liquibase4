package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.core.SnapshotIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Table;

public class SnapshotIndexesLogicH2 extends SnapshotIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String indexName, boolean unique, boolean approximate, Scope scope) {

        Database database = scope.getDatabase();

        SelectDataAction sql = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "INDEXES"),
                new SelectDataAction.SelectedColumn(null, "TABLE_CATALOG", "TABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "TABLE_SCHEMA", "TABLE_SCHEM"),
                new SelectDataAction.SelectedColumn("TABLE_NAME"),
                new SelectDataAction.SelectedColumn("NON_UNIQUE"),
                new SelectDataAction.SelectedColumn(null, "TABLE_CATALOG", "INDEX_QUALIFIER"),
                new SelectDataAction.SelectedColumn("INDEX_NAME"),
                new SelectDataAction.SelectedColumn(null, "INDEX_TYPE","TYPE"),
                new SelectDataAction.SelectedColumn("ORDINAL_POSITION"),
                new SelectDataAction.SelectedColumn("COLUMN_NAME"),
                new SelectDataAction.SelectedColumn("ASC_OR_DESC"),
                new SelectDataAction.SelectedColumn("CARDINALITY"),
                new SelectDataAction.SelectedColumn("PAGES"),
                new SelectDataAction.SelectedColumn("FILTER_CONDITION"),
                new SelectDataAction.SelectedColumn("SORT_TYPE")
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
        if (indexName != null) {
            sql.addWhere("INDEX_NAME=" + database.quoteString(indexName, scope));
        }
        if (unique) {
            sql.addWhere("NON_UNIQUE=FALSE");
        }

        sql.addOrder(new SelectDataAction.OrderedByColumn("NON_UNIQUE"),
                new SelectDataAction.OrderedByColumn("TYPE"),
                new SelectDataAction.OrderedByColumn("TABLE_SCHEM"),
                new SelectDataAction.OrderedByColumn("INDEX_NAME"),
                new SelectDataAction.OrderedByColumn("ORDINAL_POSITION"));

        return sql;
    }


}

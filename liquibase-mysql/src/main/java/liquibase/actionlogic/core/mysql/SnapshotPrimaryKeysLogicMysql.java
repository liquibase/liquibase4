package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.ItemReference;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Table;

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
                errors.addError(scope.getDatabase().getShortName() + " does not support primary key names");
                break;
            }
        }
        return errors;
    }

    @Override
    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String primaryKeyName, Scope scope) {
        Database database = scope.getDatabase();

        SelectDataAction select = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "STATISTICS"),
                new SelectDataAction.SelectedColumn(null, "TABLE_SCHEMA", "TABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "NULL", "TABLE_SCHEM", true),
                new SelectDataAction.SelectedColumn("TABLE_NAME"),
                new SelectDataAction.SelectedColumn("COLUMN_NAME"),
                new SelectDataAction.SelectedColumn(null, "SEQ_IN_INDEX", "KEY_SEQ"),
                new SelectDataAction.SelectedColumn(null, "'PRIMARY'", "PK_NAME", true)
        );

        select.addWhere("INDEX_NAME='PRIMARY'");
        if (jdbcCatalogName != null) {
            select.addWhere("TABLE_SCHEMA=" + database.quoteString(jdbcCatalogName, scope));
        }
        if (tableName != null) {
            select.addWhere("TABLE_NAME=" + database.quoteString(tableName, scope));
        }

        select.addOrder(new SelectDataAction.OrderedByColumn("TABLE_SCHEMA"),
                new SelectDataAction.OrderedByColumn("TABLE_NAME"),
                new SelectDataAction.OrderedByColumn("INDEX_NAME"),
                new SelectDataAction.OrderedByColumn("SEQ_IN_INDEX"));

        return select;
    }
}

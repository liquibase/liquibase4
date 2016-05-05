package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.Catalog;
import liquibase.item.core.Schema;
import liquibase.item.core.SchemaReference;
import liquibase.item.core.Table;
import liquibase.util.StringUtil;
import liquibase.util.Validate;

import java.util.List;

/**
 * Logic to snapshot database table(s). Delegates to {@link QueryJdbcMetaDataAction} getTables().
 */
public class SnapshotTablesLogic extends AbstractSnapshotDatabaseObjectsLogic<Table> {

    @Override
    protected Class<Table> getTypeToSnapshot() {
        return Table.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Schema.class,
                Catalog.class,
                Table.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        String catalogName = null;
        String schemaName = null;
        String tableName = null;

        if (relatedTo.instanceOf(Catalog.class)) {
            catalogName = relatedTo.name;
        } else if (relatedTo.instanceOf(Schema.class)) {
            schemaName = relatedTo.name;
            List<String> nameParts = relatedTo.asList(2);
            catalogName = nameParts.get(0);
        } else if (relatedTo.instanceOf(Table.class)) {
            List<String> names = relatedTo.asList(3);
            catalogName = names.get(0);
            schemaName = names.get(1);
            tableName = names.get(2);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();
        tableName = database.escapeStringForLike(tableName);
        if (database.supports(Catalog.class, scope)) {
            return new QueryJdbcMetaDataAction("getTables", catalogName, schemaName, tableName, new String[]{"TABLE"});
        } else {
            if (database.metaDataCallsSchemasCatalogs()) {
                return new QueryJdbcMetaDataAction("getTables", schemaName, null, tableName, new String[]{"TABLE"});
            } else {
                return new QueryJdbcMetaDataAction("getTables", null, schemaName, tableName, new String[]{"TABLE"});
            }
        }
    }

    @Override
    protected Table convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String rawTableName = row.get("TABLE_NAME", String.class);
        String rawSchemaName = row.get("TABLE_SCHEM", String.class);
        String rawCatalogName = row.get("TABLE_CAT", String.class);
        String remarks = StringUtil.trimToNull(row.get("REMARKS", String.class));
        if (remarks != null) {
            remarks = remarks.replace("''", "'"); //come back escaped sometimes
        }

        SchemaReference schema;
        if (!scope.getDatabase().supports(Schema.class, scope)) {
            schema = null;
        } else if (!scope.getDatabase().supports(Catalog.class, scope)) {
            if (rawCatalogName != null && rawSchemaName == null) {
                schema = new SchemaReference(rawCatalogName);
            } else {
                schema = new SchemaReference(rawSchemaName);
            }
        } else {
            schema = new SchemaReference(rawCatalogName, rawSchemaName);
        }

        Table table = new Table(rawTableName, schema);
        table.remarks = remarks;

        if ("Y".equals(row.get("TEMPORARY", String.class))) {
            table.set("temporary", "GLOBAL");

            String duration = row.get("DURATION", String.class);
            if (duration != null && duration.equals("SYS$TRANSACTION")) {
                table.set("duration", "ON COMMIT DELETE ROWS");
            } else if (duration != null && duration.equals("SYS$SESSION")) {
                table.set("duration", "ON COMMIT PRESERVE ROWS");
            }
        }

        return table;
    }
}

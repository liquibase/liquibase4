package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtils;
import liquibase.util.Validate;

import java.util.List;

/**
 * Logic to snapshot database table(s). Delegates to {@link QueryJdbcMetaDataAction} getTables().
 */
public class SnapshotTablesLogicJdbc extends AbstractSnapshotObjectsLogicJdbc {

    @Override
    protected Class<? extends LiquibaseObject> getTypeToSnapshot() {
        return Table.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                Schema.class,
                Catalog.class,
                Table.class
        };
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
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
        if (database.supports(Catalog.class)) {
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
    protected LiquibaseObject convertToObject(RowBasedQueryResult.Row row, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        String rawTableName = row.get("TABLE_NAME", String.class);
        String rawSchemaName = row.get("TABLE_SCHEM", String.class);
        String rawCatalogName = row.get("TABLE_CAT", String.class);
        String remarks = StringUtils.trimToNull(row.get("REMARKS", String.class));
        if (remarks != null) {
            remarks = remarks.replace("''", "'"); //come back escaped sometimes
        }

        ObjectReference container;
        if (!scope.getDatabase().supports(Schema.class)) {
            container = null;
        } else if (!scope.getDatabase().supports(Catalog.class)) {
            if (rawCatalogName != null && rawSchemaName == null) {
                container = new ObjectReference(Schema.class, rawCatalogName);
            } else {
                container = new ObjectReference(Schema.class, rawSchemaName);
            }
        } else {
            container = new ObjectReference(Schema.class, new ObjectReference(Catalog.class, rawCatalogName), rawSchemaName);
        }

        Table table = new Table(new ObjectReference(container, rawTableName));
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

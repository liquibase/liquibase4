package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.util.Validate;

public class SnapshotSchemasLogic extends AbstractSnapshotDatabaseObjectsLogic<Schema> {

    @Override
    protected Class<Schema> getTypeToSnapshot() {
        return Schema.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        String catalogName;
        String schemaName;
        if (relatedTo instanceof CatalogReference) {
            if (!scope.getDatabase().supports(Catalog.class, scope)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            catalogName = ((CatalogReference) relatedTo).name;
            schemaName = null;
        } else if (relatedTo instanceof SchemaReference) {
            if (((SchemaReference) relatedTo).container == null) {
                catalogName = null;
            } else {
                catalogName = ((SchemaReference) relatedTo).container.name;
            }

            schemaName = ((SchemaReference) relatedTo).name;
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        if (scope.getDatabase() instanceof AbstractJdbcDatabase && ((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
            return createCatalogSnapshotAction(schemaName, scope);
        } else {
            return createSchemaSnapshotAction(catalogName, schemaName, scope);
        }
    }

    protected Action createCatalogSnapshotAction(String jdbcCatalogName, Scope scope) {
        return new QueryJdbcMetaDataAction("getCatalogs");
    }

    protected Action createSchemaSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, Scope scope) {
        return new QueryJdbcMetaDataAction("getSchemas", jdbcCatalogName, jdbcSchemaName);
    }


    @Override
    protected Schema convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        if (scope.getDatabase() instanceof AbstractJdbcDatabase && ((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
            String schemaName = row.get("TABLE_CAT", String.class);
            if (relatedTo.name != null && !relatedTo.name.equals(schemaName)) {
                return null;
            } else {
                return new Schema(schemaName);
            }
        } else {
            CatalogReference catalogRef = null;
            String catalogName = row.get("TABLE_CATALOG", String.class);
            if (catalogName != null) {
                catalogRef = new CatalogReference(catalogName);
            }

            return new Schema(row.get("TABLE_SCHEMA", String.class), catalogRef);
        }
    }

}

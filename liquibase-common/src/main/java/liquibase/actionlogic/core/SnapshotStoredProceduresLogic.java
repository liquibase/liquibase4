package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;

/**
 * Snapshot stored procedures.
 * Liquibase has limited support for stored procedures and so only relies on the {@link java.sql.DatabaseMetaData#getProcedures(String, String, String)} method which can
 * determine what procedures there are, but no additional information including no body.
 */
public class SnapshotStoredProceduresLogic extends AbstractSnapshotDatabaseObjectsLogic<StoredProcedure> {

    @Override
    protected Class<StoredProcedure> getTypeToSnapshot() {
        return StoredProcedure.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Catalog.class,
                Schema.class,
                StoredProcedure.class,
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {

        String catalogName = null;
        String schemaName = null;
        String procedureName = null;

        if (relatedTo instanceof CatalogReference) {
            catalogName = ((CatalogReference) relatedTo).name;
        } else if (relatedTo instanceof SchemaReference) {
            schemaName = ((SchemaReference) relatedTo).name;
            if (((SchemaReference) relatedTo).container != null) {
                catalogName = ((SchemaReference) relatedTo).container.name;
            }
        } else if (relatedTo.instanceOf(StoredProcedure.class)) {
            CatalogReference catalog = null;
            SchemaReference schema = null;
            if (relatedTo.container instanceof CatalogReference) {
                catalog = (CatalogReference) relatedTo.container;
            } else if (relatedTo.container instanceof SchemaReference) {
                schema = (SchemaReference) relatedTo.container;
                catalog = schema.container;
            }

            if (catalog != null) {
                catalogName = catalog.name;
            }
            if (schema != null) {
                schemaName = schema.name;
            }

            procedureName = relatedTo.name;
        } else {
            throw new UnexpectedLiquibaseException("Unknown relatedTo type: " + relatedTo);
        }

        AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();

        if (catalogName == null && database.metaDataCallsSchemasCatalogs()) {
            catalogName = schemaName;
        }

        return new QueryJdbcMetaDataAction("getProcedures", catalogName, schemaName, database.escapeStringForLike(procedureName));
    }


    @Override
    protected StoredProcedure convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String procedureCatalog = row.get("PROCEDURE_CAT", String.class);
        String procedureSchema = row.get("PROCEDURE_SCHEMA", String.class);
        String procedureName = row.get("PROCEDURE_NAME", String.class);
        String remarks = row.get("REMARKS", String.class);

        DatabaseObjectReference container = null;
        if (procedureCatalog != null) {
            container = new CatalogReference(procedureCatalog);
        }
        if (procedureSchema != null) {
            container = new SchemaReference(procedureSchema, procedureCatalog);
        }

        StoredProcedure procedure = new StoredProcedure(procedureName, container);

        procedure.remarks = remarks;

        return procedure;

    }
}

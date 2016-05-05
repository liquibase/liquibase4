package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.*;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.util.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapshotPrimaryKeysLogic extends AbstractSnapshotDatabaseObjectsLogic<PrimaryKey> {

    @Override
    protected Class<PrimaryKey> getTypeToSnapshot() {
        return PrimaryKey.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                PrimaryKey.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {

        PrimaryKeyReference pkRef;
        if (relatedTo instanceof CatalogReference) {
            if (!scope.getDatabase().supports(Catalog.class, scope)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            pkRef = new PrimaryKeyReference(null, new RelationReference(Table.class, null, new SchemaReference(null, (CatalogReference) relatedTo)));
        } else if (relatedTo instanceof SchemaReference) {
            pkRef = new PrimaryKeyReference(null, new RelationReference(Table.class, null, (SchemaReference) relatedTo));
        } else if (relatedTo instanceof RelationReference) {
            pkRef = new PrimaryKeyReference(null, (RelationReference) relatedTo);
        } else if (relatedTo instanceof PrimaryKeyReference) {
            pkRef = new PrimaryKeyReference(relatedTo.name, ((PrimaryKeyReference) relatedTo).container);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = pkRef.asList(4);

        if (scope.getDatabase().supports(Catalog.class, scope)) {
            return createSnapshotAction(nameParts.get(0), nameParts.get(1), nameParts.get(2), nameParts.get(3), scope);
        } else {
            if (((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
                return createSnapshotAction(nameParts.get(1), null, nameParts.get(2), nameParts.get(3), scope);
            } else {
                return createSnapshotAction(null, nameParts.get(1), nameParts.get(2), nameParts.get(3), scope);
            }
        }
    }

    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String primaryKeyName, Scope scope) {
        return new QueryJdbcMetaDataAction("getPrimaryKeys", jdbcCatalogName, jdbcSchemaName, tableName);
    }

    @Override
    protected PrimaryKey convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String pkName = row.get("PK_NAME", String.class);
        String columnName = row.get("COLUMN_NAME", String.class);
        Integer position = row.get("KEY_SEQ", Integer.class);

        String tableCat = row.get("TABLE_CAT", String.class);
        String tableSchema = row.get("TABLE_SCHEM", String.class);
        String tableName = row.get("TABLE_NAME", String.class);

        PrimaryKeyReference pkReference;
        if (tableCat != null && tableSchema == null) {
            pkReference = new PrimaryKeyReference(pkName, new RelationReference(Table.class, tableCat, tableName));
        } else {
            pkReference = new PrimaryKeyReference(pkName, new RelationReference(Table.class, tableCat, tableSchema, tableName));
        }

        PrimaryKey pk = new PrimaryKey(pkReference.name, pkReference.container);
        PrimaryKey.PrimaryKeyColumn pkColumn = new PrimaryKey.PrimaryKeyColumn(columnName);
        pk.columns.add(pkColumn);

        return pk;
    }

    @Override
    protected DelegateResult.Modifier createModifier(DatabaseObjectReference relatedTo, final SnapshotItemsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<PrimaryKey> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(PrimaryKey.class);
                Map<PrimaryKeyReference, PrimaryKey> combinedResults = new HashMap<>();
                for (PrimaryKey primaryKey : rawResults) {
                    PrimaryKey existingPk = combinedResults.get(primaryKey.toReference());
                    if (existingPk == null) {
                        combinedResults.put((PrimaryKeyReference) primaryKey.toReference(), primaryKey);
                    } else {
                        existingPk.columns.addAll(primaryKey.columns);
                    }
                }

                return new ObjectBasedQueryResult(originalAction, new ArrayList(combinedResults.values()));
            }
        };
    }
}

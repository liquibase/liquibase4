package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.*;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.Validate;

import java.util.*;

public class SnapshotPrimaryKeysLogic extends AbstractSnapshotDatabaseObjectsLogic<PrimaryKey> {

    @Override
    protected Class<PrimaryKey> getTypeToSnapshot() {
        return PrimaryKey.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                PrimaryKey.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {

        PrimaryKey.PrimaryKeyReference objectReference;
        if (relatedTo.instanceOf(Catalog.class)) {
            if (!scope.getDatabase().supports(Catalog.class)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            objectReference = new PrimaryKey.PrimaryKeyReference(new ObjectReference(Table.class, relatedTo.name, null, null), null);
        } else if (relatedTo.instanceOf(Schema.class)) {
            objectReference = new PrimaryKey.PrimaryKeyReference(new ObjectReference(Table.class, relatedTo, null), null);
        } else if (relatedTo.instanceOf(Table.class)) {
            objectReference = new PrimaryKey.PrimaryKeyReference(relatedTo, null);
        } else if (relatedTo.instanceOf(PrimaryKey.class)) {
            objectReference = new PrimaryKey.PrimaryKeyReference(relatedTo.container, relatedTo.name);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = objectReference.asList(4);

        if (scope.getDatabase().supports(Catalog.class)) {
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
    protected PrimaryKey convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String pkName = row.get("PK_NAME", String.class);
        String columnName = row.get("COLUMN_NAME", String.class);
        Integer position = row.get("KEY_SEQ", Integer.class);

        String tableCat = row.get("TABLE_CAT", String.class);
        String tableSchema = row.get("TABLE_SCHEM", String.class);
        String tableName = row.get("TABLE_NAME", String.class);

        PrimaryKey.PrimaryKeyReference pkReference;
        if (tableCat != null && tableSchema == null) {
            pkReference = new PrimaryKey.PrimaryKeyReference(new ObjectReference(Table.class, tableCat, tableName), pkName);
        } else {
            pkReference = new PrimaryKey.PrimaryKeyReference(new ObjectReference(Table.class, tableCat, tableSchema, tableName), pkName);
        }

        PrimaryKey pk = new PrimaryKey(pkReference);
        PrimaryKey.PrimaryKeyColumn pkColumn = new PrimaryKey.PrimaryKeyColumn(columnName);
        pk.columns.add(pkColumn);

        return pk;
    }

    @Override
    protected DelegateResult.Modifier createModifier(ObjectReference relatedTo, final SnapshotObjectsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<PrimaryKey> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(PrimaryKey.class);
                Map<PrimaryKey.PrimaryKeyReference, PrimaryKey> combinedResults = new HashMap<>();
                for (PrimaryKey primaryKey : rawResults) {
                    PrimaryKey existingPk = combinedResults.get(primaryKey.toReference());
                    if (existingPk == null) {
                        combinedResults.put((PrimaryKey.PrimaryKeyReference) primaryKey.toReference(), primaryKey);
                    } else {
                        existingPk.columns.addAll(primaryKey.columns);
                    }
                }

                return new ObjectBasedQueryResult(originalAction, new ArrayList(combinedResults.values()));
            }
        };
    }
}

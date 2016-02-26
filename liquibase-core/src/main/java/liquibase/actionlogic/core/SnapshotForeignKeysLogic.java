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
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.util.Validate;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapshotForeignKeysLogic extends AbstractSnapshotDatabaseObjectsLogic<ForeignKey> {

    @Override
    protected Class<ForeignKey> getTypeToSnapshot() {
        return ForeignKey.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                ForeignKey.class,
                Relation.class,
                Schema.class,
                Catalog.class
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
            List<String> names = relatedTo.asList(2);
            catalogName = names.get(0);
            schemaName = names.get(1);
        } else if (relatedTo.instanceOf(Relation.class)) {
            List<String> names = relatedTo.asList(3);
            catalogName = names.get(0);
            schemaName = names.get(1);
            tableName = names.get(2);
        } else if (relatedTo.instanceOf(ForeignKey.class)) {
            List<String> names = relatedTo.asList(4);
            catalogName = names.get(0);
            schemaName = names.get(1);
            tableName = names.get(2);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        if (scope.getDatabase().supports(Catalog.class, scope)) {
            return new QueryJdbcMetaDataAction("getImportedKeys", catalogName, schemaName, tableName);
        } else {
            if (((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
                return new QueryJdbcMetaDataAction("getImportedKeys", schemaName, null, tableName);
            } else {
                return new QueryJdbcMetaDataAction("getImportedKeys", null, schemaName, tableName);
            }
        }
    }

    @Override
    protected ForeignKey convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String relatedToForeignKeyName = null;
        if (relatedTo.instanceOf(ForeignKey.class)) {
            relatedToForeignKeyName = relatedTo.name;
        }

        String pkTableCat = row.get("PKTABLE_CAT", String.class);
        String pkTableSchema = row.get("PKTABLE_SCHEM", String.class);
        String pkTableName = row.get("PKTABLE_NAME", String.class);
        String pkColumnName = row.get("PKCOLUMN_NAME", String.class);

        String fkTableCat = row.get("FKTABLE_CAT", String.class);
        String fkTableSchema = row.get("FKTABLE_SCHEM", String.class);
        String fkTableName = row.get("FKTABLE_NAME", String.class);
        String fkColumnName = row.get("FKCOLUMN_NAME", String.class);
        Short updateRule = row.get("UPDATE_RULE", Short.class);
        Short deleteRule = row.get("DELETE_RULE", Short.class);
        String fkName = row.get("FK_NAME", String.class);
        Short deferrability = row.get("DEFERRABILITY", Short.class);

        if (relatedToForeignKeyName != null && fkName != null && !fkName.equals(relatedToForeignKeyName)) {
            return null;
        }

        ForeignKeyReference fkRef;
        if (fkTableCat != null && fkTableSchema == null) {
            fkRef = new ForeignKeyReference(fkTableCat, fkTableName, fkName);
        } else {
            fkRef = new ForeignKeyReference(fkTableCat, fkTableSchema, fkTableName, fkName);
        }

        RelationReference refTableRef;
        if (pkTableCat != null && pkTableSchema == null) {
            refTableRef = new RelationReference(Table.class, pkTableCat, pkTableName);
        } else {
            refTableRef = new RelationReference(Table.class, pkTableCat, pkTableSchema, pkTableName);
        }

        ForeignKey fk = new ForeignKey(fkRef.name, fkRef.container);
        fk.referencedTable = refTableRef;
        fk.columnChecks.add(new ForeignKey.ForeignKeyColumnCheck(fkColumnName, pkColumnName));

        if (updateRule != null) {
            switch (updateRule) {
                case(DatabaseMetaData.importedKeyNoAction):
                    fk.updateRule = ForeignKey.ReferentialAction.noAction;
                    break;
                case(DatabaseMetaData.importedKeyCascade):
                    fk.updateRule = ForeignKey.ReferentialAction.cascade;
                    break;
                case(DatabaseMetaData.importedKeySetNull):
                    fk.updateRule = ForeignKey.ReferentialAction.setNull;
                    break;
                case(DatabaseMetaData.importedKeySetDefault):
                    fk.updateRule = ForeignKey.ReferentialAction.setDefault;
                    break;
                case(DatabaseMetaData.importedKeyRestrict):
                    fk.updateRule = ForeignKey.ReferentialAction.restrict;
                    break;
            }
        }

        if (deleteRule != null) {
            switch (deleteRule) {
                case(DatabaseMetaData.importedKeyNoAction):
                    fk.deleteRule = ForeignKey.ReferentialAction.noAction;
                    break;
                case(DatabaseMetaData.importedKeyCascade):
                    fk.deleteRule = ForeignKey.ReferentialAction.cascade;
                    break;
                case(DatabaseMetaData.importedKeySetNull):
                    fk.deleteRule = ForeignKey.ReferentialAction.setNull;
                    break;
                case(DatabaseMetaData.importedKeySetDefault):
                    fk.deleteRule = ForeignKey.ReferentialAction.setDefault;
                    break;
                case(DatabaseMetaData.importedKeyRestrict):
                    fk.deleteRule = ForeignKey.ReferentialAction.restrict;
                    break;
            }
        }

        if (deferrability != null) {
            switch (deferrability) {
                case (DatabaseMetaData.importedKeyInitiallyDeferred):
                    fk.deferrable = true;
                    fk.initiallyDeferred = true;
                    break;
                case (DatabaseMetaData.importedKeyInitiallyImmediate):
                    fk.deferrable = true;
                    fk.initiallyDeferred = false;
                    break;
                case (DatabaseMetaData.importedKeyNotDeferrable):
                    fk.deferrable = false;
                    fk.initiallyDeferred = false;
                    break;
            }
        }
        return fk;
}

    @Override
    protected DelegateResult.Modifier createModifier(DatabaseObjectReference relatedTo, final SnapshotItemsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<ForeignKey> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(ForeignKey.class);
                Map<ForeignKeyReference, ForeignKey> combinedResults = new HashMap<>();
                for (ForeignKey foreignKey : rawResults) {
                    ForeignKey existingPk = combinedResults.get(foreignKey.toReference());
                    if (existingPk == null) {
                        combinedResults.put(foreignKey.toReference(), foreignKey);
                    } else {
                        existingPk.columnChecks.addAll(foreignKey.columnChecks);
                    }
                }

                return new ObjectBasedQueryResult(originalAction, new ArrayList(combinedResults.values()));
            }
        };
    }
}

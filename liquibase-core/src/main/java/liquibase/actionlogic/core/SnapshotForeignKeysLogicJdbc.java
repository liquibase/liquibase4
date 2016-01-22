package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.Validate;

import java.sql.DatabaseMetaData;
import java.util.*;

public class SnapshotForeignKeysLogicJdbc extends AbstractSnapshotObjectsLogicJdbc {

    @Override
    protected Class<? extends LiquibaseObject> getTypeToSnapshot() {
        return ForeignKey.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                ForeignKey.class,
                Relation.class,
                Schema.class,
                Catalog.class
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
            List<String> names = relatedTo.asList(2);
            catalogName = names.get(0);
            schemaName = names.get(1);
        } else if (relatedTo.instanceOf(Table.class)) {
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

        if (scope.getDatabase().supports(Catalog.class)) {
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
    protected LiquibaseObject convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
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
        Integer position = row.get("KEY_SEQ", Integer.class);
        Short updateRule = row.get("UPDATE_RULE", Short.class);
        Short deleteRule = row.get("DELETE_RULE", Short.class);
        String fkName = row.get("FK_NAME", String.class);
        Short deferrability = row.get("DEFERRABILITY", Short.class);

        if (relatedToForeignKeyName != null && fkName != null && !fkName.equals(relatedToForeignKeyName)) {
            return null;
        }

        ForeignKey.ForeignKeyReference objectReference;
        if (fkTableCat != null && fkTableSchema == null) {
            objectReference = new ForeignKey.ForeignKeyReference(fkTableCat, fkTableName, fkName);
        } else {
            objectReference = new ForeignKey.ForeignKeyReference(fkTableCat, fkTableSchema, fkTableName, fkName);
        }

        ObjectReference pkTableObjectReference;
        if (pkTableCat != null && pkTableSchema == null) {
            pkTableObjectReference = new ObjectReference(Table.class, pkTableCat, pkTableName);
        } else {
            pkTableObjectReference = new ObjectReference(Table.class, pkTableCat, pkTableSchema, pkTableName);
        }

        ForeignKey fk = new ForeignKey(objectReference);
        fk.columnChecks.add(new ForeignKey.ForeignKeyColumnCheck(fkColumnName, pkColumnName, position));

        if (updateRule != null) {
            switch (updateRule) {
                case(DatabaseMetaData.importedKeyNoAction):
                    fk.updateRule = ForeignKey.ConstraintType.importedKeyNoAction;
                    break;
                case(DatabaseMetaData.importedKeyCascade):
                    fk.updateRule = ForeignKey.ConstraintType.importedKeyCascade;
                    break;
                case(DatabaseMetaData.importedKeySetNull):
                    fk.updateRule = ForeignKey.ConstraintType.importedKeySetNull;
                    break;
                case(DatabaseMetaData.importedKeySetDefault):
                    fk.updateRule = ForeignKey.ConstraintType.importedKeySetDefault;
                    break;
                case(DatabaseMetaData.importedKeyRestrict):
                    fk.updateRule = ForeignKey.ConstraintType.importedKeyRestrict;
                    break;
            }
        }

        if (deleteRule != null) {
            switch (deleteRule) {
                case(DatabaseMetaData.importedKeyNoAction):
                    fk.deleteRule = ForeignKey.ConstraintType.importedKeyNoAction;
                    break;
                case(DatabaseMetaData.importedKeyCascade):
                    fk.deleteRule = ForeignKey.ConstraintType.importedKeyCascade;
                    break;
                case(DatabaseMetaData.importedKeySetNull):
                    fk.deleteRule = ForeignKey.ConstraintType.importedKeySetNull;
                    break;
                case(DatabaseMetaData.importedKeySetDefault):
                    fk.deleteRule = ForeignKey.ConstraintType.importedKeySetDefault;
                    break;
                case(DatabaseMetaData.importedKeyRestrict):
                    fk.deleteRule = ForeignKey.ConstraintType.importedKeyRestrict;
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
    protected ActionResult.Modifier createModifier(ObjectReference relatedTo, final SnapshotObjectsAction originalAction, Scope scope) {
        return new SnapshotModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<ForeignKey> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(ForeignKey.class);
                Map<ObjectReference, ForeignKey> combinedResults = new HashMap<>();
                for (ForeignKey foreignKey : rawResults) {
                    ForeignKey existingPk = combinedResults.get(foreignKey.toReference());
                    if (existingPk == null) {
                        combinedResults.put(foreignKey.toReference(), foreignKey);
                    } else {
                        existingPk.columnChecks.addAll(foreignKey.columnChecks);
                    }
                }

                for (ForeignKey foreignKey : combinedResults.values()) {
                    Collections.sort(foreignKey.columnChecks, new Comparator<ForeignKey.ForeignKeyColumnCheck>() {
                        @Override
                        public int compare(ForeignKey.ForeignKeyColumnCheck o1, ForeignKey.ForeignKeyColumnCheck o2) {
                            if (o1.position == null || o2.position == null) {
                                return o1.baseColumn.compareTo(o2.baseColumn);
                            } else {
                                return o1.position.compareTo(o2.position);
                            }
                        }
                    });
                }

                return new ObjectBasedQueryResult(new ArrayList(combinedResults.values()), originalAction);
            }
        };
    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.actionlogic.RowBasedQueryResult;
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
    protected Action createSnapshotAction(SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        ObjectReference relatedTo = action.relatedTo;
        ObjectReference fkName = null;
        ObjectReference tableName = null;
        if (relatedTo.instanceOf(Catalog.class)) {
            fkName = new ObjectReference(relatedTo.name, null, null, null);
        } else if (relatedTo.instanceOf(Schema.class)) {
            fkName = new ObjectReference(relatedTo.container, relatedTo.name, null, null);
        } else if (relatedTo.instanceOf(Table.class)) {
            tableName = relatedTo;
        } else if (relatedTo.instanceOf(ForeignKey.class)) {
            fkName = relatedTo;
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        if (tableName == null) {
            tableName = new ObjectReference(fkName.container, null);
        }

        List<String> nameParts = fkName.truncate(3).asList(3);

        if (scope.getDatabase().supports(Catalog.class)) {
            return new QueryJdbcMetaDataAction("getImportedKeys", nameParts.get(0), nameParts.get(1), nameParts.get(2));
        } else { //usually calls the top level "catalogs"
            return new QueryJdbcMetaDataAction("getImportedKeys", nameParts.get(1), null, nameParts.get(2));
        }
    }

    @Override
    protected LiquibaseObject convertToObject(RowBasedQueryResult.Row row, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
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

        ObjectReference objectReference;
        if (fkTableCat != null && fkTableSchema == null) {
            objectReference = new ObjectReference(fkTableCat, fkName);
        } else {
            objectReference = new ObjectReference(fkTableCat, fkTableSchema, fkName);
        }
        ObjectReference fkTableObjectReference;
        if (fkTableCat != null && fkTableSchema == null) {
            fkTableObjectReference = new ObjectReference(fkTableCat, fkTableName);
        } else {
            fkTableObjectReference = new ObjectReference(fkTableCat, fkTableSchema, fkTableName);
        }
        ObjectReference pkTableObjectReference;
        if (pkTableCat != null && pkTableSchema == null) {
            pkTableObjectReference = new ObjectReference(pkTableCat, pkTableName);
        } else {
            pkTableObjectReference = new ObjectReference(pkTableCat, pkTableSchema, pkTableName);
        }

        ForeignKey fk = new ForeignKey(objectReference);
        fk.columnChecks.add(new ForeignKey.ForeignKeyColumnCheck(new ObjectReference(fkTableObjectReference, fkColumnName), new ObjectReference(pkTableObjectReference, pkColumnName), position));

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
    protected ActionResult.Modifier createModifier(SnapshotObjectsAction originalAction, Scope scope) {
        return new SnapshotModifier(originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<ForeignKey> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(ForeignKey.class);
                Map<ObjectReference, ForeignKey> combinedResults = new HashMap<>();
                for (ForeignKey foreignKey : rawResults) {
                    ForeignKey existingPk = combinedResults.get(foreignKey.name);
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
                                return o1.baseColumn.name.compareTo(o2.baseColumn.name);
                            } else {
                                return o1.position.compareTo(o2.position);
                            }
                        }
                    });
                }

                return new ObjectBasedQueryResult(new ArrayList(combinedResults.values()));
            }
        };
    }
}

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

import java.util.*;

public class SnapshotPrimaryKeysLogicJdbc extends AbstractSnapshotObjectsLogicJdbc {

    @Override
    protected Class<? extends LiquibaseObject> getTypeToSnapshot() {
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
            return new QueryJdbcMetaDataAction("getPrimaryKeys", nameParts.get(0), nameParts.get(1), nameParts.get(2));
        } else {
            if (((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
                return new QueryJdbcMetaDataAction("getPrimaryKeys", nameParts.get(1), null, nameParts.get(2));
            } else {
                return new QueryJdbcMetaDataAction("getPrimaryKeys", null, nameParts.get(1), nameParts.get(2));
            }
        }
    }

    @Override
    protected LiquibaseObject convertToObject(RowBasedQueryResult.Row row, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        String pkName = row.get("PK_NAME", String.class);
        String columnName = row.get("COLUMN_NAME", String.class);
        Integer position = row.get("KEY_SEQ", Integer.class);
        String ascOrDesc = row.get("ASC_OR_DESC", String.class);
        Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : "A".equals(ascOrDesc) ? Boolean.FALSE : null;

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
        PrimaryKey.PrimaryKeyColumn pkColumn = new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference(pkReference.getTable(), columnName));
        pkColumn.descending = descending;
        pkColumn.position = position;
        pk.columns.add(pkColumn);

        return pk;
    }

    @Override
    protected ActionResult.Modifier createModifier(ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) {
        return new SnapshotModifier(relatedTo, originalAction, scope) {
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

                for (PrimaryKey primaryKey : combinedResults.values()) {
                    Collections.sort(primaryKey.columns, new Comparator<PrimaryKey.PrimaryKeyColumn>() {
                        @Override
                        public int compare(PrimaryKey.PrimaryKeyColumn o1, PrimaryKey.PrimaryKeyColumn o2) {
                            if (o1.position == null || o2.position == null) {
                                return o1.name.compareTo(o2.name);
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

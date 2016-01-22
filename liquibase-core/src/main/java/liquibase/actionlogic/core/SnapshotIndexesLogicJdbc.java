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
import liquibase.util.StringUtils;
import liquibase.util.Validate;

import java.sql.DatabaseMetaData;
import java.util.*;

public class SnapshotIndexesLogicJdbc extends AbstractSnapshotObjectsLogicJdbc {

    @Override
    protected Class<? extends LiquibaseObject> getTypeToSnapshot() {
        return Index.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                PrimaryKey.class,
                Index.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    public ActionResult execute(SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        List<Action> pkActions = new ArrayList<>();
        for (ObjectReference relatedTo : action.relatedTo) {
            if (relatedTo.instanceOf(PrimaryKey.class)) {
                pkActions.add(new SnapshotObjectsAction(relatedTo));
            }
        }
        ActionResult result = super.execute(action, scope);
//        if (pkActions.size() > 0) {
//            result = new DelegateResult(action, null, (DelegateResult) result, pkActions.toArray(new Action[pkActions.size()]));
//        }
        return result;
    }

    @Override
    public ActionResult execute(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        if (relatedTo.instanceOf(PrimaryKey.class)) {
            return new DelegateResult(action, new PrimaryKeyIndexModifier(action), new SnapshotObjectsAction(Index.class, relatedTo.container), new SnapshotObjectsAction(relatedTo));
        } else {
            return super.execute(relatedTo, action, scope);
        }
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {

        final Boolean DEFAULT_UNIQUE_PARAM = Boolean.FALSE;
        final Boolean DEFAULT_APPROX_PARAM = Boolean.TRUE;

        Index.IndexReference objectReference;
        if (relatedTo.instanceOf(Catalog.class)) {
            if (!scope.getDatabase().supports(Catalog.class)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            objectReference = new Index.IndexReference(new ObjectReference(Schema.class, relatedTo, null), null);
        } else if (relatedTo.instanceOf(Schema.class)) {
            objectReference = new Index.IndexReference(new ObjectReference(Table.class, relatedTo, null), null);
        } else if (relatedTo.instanceOf(Table.class)) {
            objectReference = new Index.IndexReference(relatedTo, null);
        } else if (relatedTo.instanceOf(Index.class)) {
            objectReference = new Index.IndexReference(relatedTo.container, relatedTo.name);
        } else if (relatedTo.instanceOf(PrimaryKey.class)) {
            objectReference = new Index.IndexReference(relatedTo.container, null);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = objectReference.asList(4);

        if (scope.getDatabase().supports(Catalog.class)) {
            return new QueryJdbcMetaDataAction("getIndexInfo", nameParts.get(0), nameParts.get(1), nameParts.get(2), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM);
        } else {
            if (((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
                return new QueryJdbcMetaDataAction("getIndexInfo", nameParts.get(1), null, nameParts.get(2), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM);
            } else {
                return new QueryJdbcMetaDataAction("getIndexInfo", null, nameParts.get(1), nameParts.get(2), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM);
            }
        }
    }

    @Override
    protected LiquibaseObject convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        short type = row.get("TYPE", Short.class);
        if (type == DatabaseMetaData.tableIndexStatistic) {
            return null;
        }


        String indexName = row.get("INDEX_NAME", String.class);
        String indexQualifier = row.get("INDEX_QUALIFIER", String.class);
        String columnName = row.get("COLUMN_NAME", String.class);
        Integer position = row.get("ORDINAL_POSITION", Integer.class);
        String ascOrDesc = row.get("ASC_OR_DESC", String.class);
        Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : "A".equals(ascOrDesc) ? Boolean.FALSE : null;

        String tableCat = row.get("TABLE_CAT", String.class);
        String tableSchema = row.get("TABLE_SCHEM", String.class);
        String tableName = row.get("TABLE_NAME", String.class);
        String definition = StringUtils.trimToNull(row.get("FILTER_CONDITION", String.class));
        Boolean nonUnique = row.get("NON_UNIQUE", Boolean.class);
        if (nonUnique == null) {
            nonUnique = true;
        }


        if (columnName == null && definition == null) {
            //nothing to index, not sure why these come through sometimes
            return null;
        }


        Index.IndexReference indexReference;
        if (tableCat != null && tableSchema == null) {
            indexReference = new Index.IndexReference(new ObjectReference(Table.class, tableCat, tableName), indexName);
        } else {
            indexReference = new Index.IndexReference(new ObjectReference(Table.class, tableCat, tableSchema, tableName), indexName);
        }

        Index index = new Index(indexReference);
        if (indexQualifier != null) {
            index.indexContainer = new ObjectReference(Schema.class, indexQualifier);
        }
        index.unique = !nonUnique;
        index.clustered = type == DatabaseMetaData.tableIndexClustered;


        Index.IndexedColumn indexedColumn;
        if (definition == null) {
            indexedColumn = new Index.IndexedColumn(new Column.ColumnReference(indexReference.getTable(), columnName));
            indexedColumn.computed = false;
        } else {
            indexedColumn = new Index.IndexedColumn(new Column.ColumnReference(indexReference.getTable(), definition));
            indexedColumn.computed = true;
        }
        indexedColumn.descending = descending;
        indexedColumn.position = position;
        index.columns.add(indexedColumn);

        return index;
    }

    @Override
    protected ActionResult.Modifier createModifier(final ObjectReference relatedTo, final SnapshotObjectsAction originalAction, Scope scope) {
        return new SnapshotModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                if (result instanceof ObjectBasedQueryResult) {
                    return result;
                }

                List<Index> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(Index.class);
                Map<Index.IndexReference, Index> combinedResults = new HashMap<>();
                for (Index Index : rawResults) {
                    Index existingPk = combinedResults.get(Index.toReference());
                    if (existingPk == null) {
                        combinedResults.put((Index.IndexReference) Index.toReference(), Index);
                    } else {
                        existingPk.columns.addAll(Index.columns);
                    }
                }

                for (Index Index : combinedResults.values()) {
                    Collections.sort(Index.columns, new Comparator<Index.IndexedColumn>() {
                        @Override
                        public int compare(Index.IndexedColumn o1, Index.IndexedColumn o2) {
                            if (o1.position == null || o2.position == null) {
                                return o1.name.compareTo(o2.name);
                            } else {
                                return o1.position.compareTo(o2.position);
                            }
                        }
                    });
                }

                if (relatedTo.instanceOf(PrimaryKey.class)) {

                }

                return new ObjectBasedQueryResult(new ArrayList(combinedResults.values()), originalAction);
            }
        };
    }

    private static class PrimaryKeyIndexModifier implements ActionResult.Modifier {
        private SnapshotObjectsAction originalAction;

        public PrimaryKeyIndexModifier(SnapshotObjectsAction originalAction) {
            this.originalAction = originalAction;
        }

        @Override
        public ActionResult rewrite(ActionResult result) throws ActionPerformException {
            List<ActionResult> nestedResults = ((CompoundResult) result).getNestedResults();
            List<PrimaryKey> primaryKeys = new ArrayList<>();
            List<Index> indexes = new ArrayList<>();

            for (ActionResult subresult : nestedResults) {
                Class<? extends LiquibaseObject> type = ((SnapshotObjectsAction) subresult.getSourceAction()).typeToSnapshot;
                if (type.isAssignableFrom(PrimaryKey.class)) {
                    primaryKeys.addAll(((ObjectBasedQueryResult) subresult).asList(PrimaryKey.class));
                } else if (type.isAssignableFrom(Index.class)) {
                    indexes.addAll(((ObjectBasedQueryResult) subresult).asList(Index.class));
                } else {
                    throw new ActionPerformException("Unexpected reference type: " + type.getName());
                }
            }

            List<Index> finalResults = new ArrayList<>();
            for (PrimaryKey primaryKey : primaryKeys) {
                for (Index index : indexes) {
                    if (isBackingIndex(index, primaryKey)) {
                        finalResults.add(index);
                        break;
                    }
                }
            }

            return new ObjectBasedQueryResult(finalResults, originalAction);
        }

        protected boolean isBackingIndex(Index index, PrimaryKey primaryKey) {
            if (!index.table.equals(primaryKey.table, true)) {
                return false;
            }
            if (index.columns.size() != primaryKey.columns.size()) {
                return false;
            }
            for (int i = 0; i < index.columns.size(); i++) {
                if (!index.columns.get(i).name.equals(primaryKey.columns.get(i).name)) {
                    return false;
                }
            }
            return true;
        }


    }
}

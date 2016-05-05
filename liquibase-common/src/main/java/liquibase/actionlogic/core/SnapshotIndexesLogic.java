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
import liquibase.util.StringUtil;
import liquibase.util.Validate;

import java.sql.DatabaseMetaData;
import java.util.*;

public class SnapshotIndexesLogic extends AbstractSnapshotDatabaseObjectsLogic<Index> {

    @Override
    protected Class<Index> getTypeToSnapshot() {
        return Index.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                PrimaryKey.class,
                Index.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    public ActionResult execute(ItemReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        if (relatedTo.instanceOf(PrimaryKey.class)) {
            return new DelegateResult(action, new PrimaryKeyIndexModifier(action), new SnapshotItemsAction(Index.class, relatedTo.container), new SnapshotItemsAction(relatedTo));
        } else {
            return super.execute(relatedTo, action, scope);
        }
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {

        final Boolean DEFAULT_UNIQUE_PARAM = Boolean.FALSE;
        final Boolean DEFAULT_APPROX_PARAM = Boolean.TRUE;

        IndexReference indexRef;
        if (relatedTo instanceof CatalogReference) {
            if (!scope.getDatabase().supports(Catalog.class, scope)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            indexRef = new IndexReference(null, new RelationReference(Table.class, null, new SchemaReference(null, (CatalogReference) relatedTo)));
        } else if (relatedTo instanceof SchemaReference) {
            indexRef = new IndexReference(null, new RelationReference(Table.class, null, (SchemaReference) relatedTo));
        } else if (relatedTo instanceof RelationReference) {
            indexRef = new IndexReference(null, (RelationReference) relatedTo);
        } else if (relatedTo instanceof IndexReference) {
            indexRef = new IndexReference(relatedTo.name, ((IndexReference) relatedTo).container);
        } else if (relatedTo instanceof PrimaryKeyReference) {
            indexRef = new IndexReference(null, ((PrimaryKeyReference) relatedTo).container);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = indexRef.asList(4);

        if (scope.getDatabase().supports(Catalog.class, scope)) {
            return createSnapshotAction(nameParts.get(0), nameParts.get(1), nameParts.get(2), nameParts.get(3), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM, scope);
        } else {
            if (((AbstractJdbcDatabase) scope.getDatabase()).metaDataCallsSchemasCatalogs()) {
                return createSnapshotAction(nameParts.get(1), null, nameParts.get(2), nameParts.get(3), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM, scope);
            } else {
                return createSnapshotAction(null, nameParts.get(1), nameParts.get(2), nameParts.get(3), DEFAULT_UNIQUE_PARAM, DEFAULT_APPROX_PARAM, scope);
            }
        }
    }

    protected Action createSnapshotAction(String jdbcCatalogName, String jdbcSchemaName, String tableName, String indexName, boolean unique, boolean approximate, Scope scope) {
        return new QueryJdbcMetaDataAction("getIndexInfo", jdbcCatalogName, jdbcSchemaName, tableName, unique, approximate);
    }

    @Override
    protected Index convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
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
        Index.IndexDirection direction = "D".equals(ascOrDesc) ? Index.IndexDirection.DESC : "A".equals(ascOrDesc) ? Index.IndexDirection.ASC : null;

        String tableCat = row.get("TABLE_CAT", String.class);
        String tableSchema = row.get("TABLE_SCHEM", String.class);
        String tableName = row.get("TABLE_NAME", String.class);
        String definition = StringUtil.trimToNull(row.get("FILTER_CONDITION", String.class));
        Boolean nonUnique = row.get("NON_UNIQUE", Boolean.class);
        if (nonUnique == null) {
            nonUnique = true;
        }


        if (columnName == null && definition == null) {
            //nothing to index, not sure why these come through sometimes
            return null;
        }


        IndexReference indexReference;
        if (tableCat != null && tableSchema == null) {
            indexReference = new IndexReference(indexName, new RelationReference(Table.class, tableCat, tableName));
        } else {
            indexReference = new IndexReference(indexName, new RelationReference(Table.class, tableCat, tableSchema, tableName));
        }

        Index index = new Index(indexReference.name, indexReference.container);
        if (indexQualifier != null) {
            index.indexSchema = new SchemaReference(indexQualifier);
        }
        index.unique = !nonUnique;
        index.clustered = type == DatabaseMetaData.tableIndexClustered;


        Index.IndexedColumn indexedColumn;
        if (definition == null) {
            indexedColumn = new Index.IndexedColumn(columnName);
            indexedColumn.computed = false;
        } else {
            indexedColumn = new Index.IndexedColumn(definition);
            indexedColumn.computed = true;
        }
        indexedColumn.direction = direction;
        indexedColumn.position = position;
        index.columns.add(indexedColumn);

        return index;
    }

    @Override
    protected DelegateResult.Modifier createModifier(final DatabaseObjectReference relatedTo, final SnapshotItemsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                if (result instanceof ObjectBasedQueryResult) {
                    return result;
                }

                List<Index> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(Index.class);
                Map<IndexReference, Index> combinedResults = new HashMap<>();
                for (Index Index : rawResults) {
                    Index existingPk = combinedResults.get(Index.toReference());
                    if (existingPk == null) {
                        combinedResults.put((IndexReference) Index.toReference(), Index);
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

                return new ObjectBasedQueryResult(originalAction, new ArrayList(combinedResults.values()));
            }
        };
    }

    private static class PrimaryKeyIndexModifier implements DelegateResult.Modifier {
        private SnapshotItemsAction originalAction;

        public PrimaryKeyIndexModifier(SnapshotItemsAction originalAction) {
            this.originalAction = originalAction;
        }

        @Override
        public ActionResult rewrite(ActionResult result) throws ActionPerformException {
            List<ActionResult> nestedResults = ((CompoundResult) result).getNestedResults();
            List<PrimaryKey> primaryKeys = new ArrayList<>();
            List<Index> indexes = new ArrayList<>();

            for (ActionResult subresult : nestedResults) {
                Class<? extends Item> type = ((SnapshotItemsAction) subresult.getSourceAction()).typeToSnapshot;
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

            return new ObjectBasedQueryResult(originalAction, finalResults);
        }

        protected boolean isBackingIndex(Index index, PrimaryKey primaryKey) {
            if (!index.relation.equals(primaryKey.relation, true)) {
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

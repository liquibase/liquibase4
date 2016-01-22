package liquibase.structure.core;

import liquibase.structure.AbstractObject;
import liquibase.structure.AbstractSchemaObject;
import liquibase.structure.AbstractTableObject;
import liquibase.structure.ObjectReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Index extends AbstractTableObject {

    public ObjectReference indexContainer;
    public List<IndexedColumn> columns = new ArrayList<>();
    public String tablespace;
    public Boolean unique;
    public Boolean clustered;

    public Index() {
    }

    public Index(String name) {
        super(name);
    }

    public Index(ObjectReference reference) {
        super(reference);
    }

    public Index(ObjectReference table, String name) {
        super(table, name);
    }

    public Index(String indexName, ObjectReference table, IndexedColumn... columns) {
        this(table, indexName);
        if (columns != null && columns.length > 0) {
            this.columns.addAll(Arrays.asList(columns));
        }
    }

    public Index(String indexName, IndexedColumn... columns) {
        this(indexName, null, columns);
    }


    @Override
    public ObjectReference toReference() {
        return new IndexReference(indexContainer, table, name);
    }

    /**
     * {@link #container} lists the table. {@link #indexContainer} can contain the index's qualifier
     */
    public static class IndexReference extends ObjectReference {

        public ObjectReference indexContainer;

        public IndexReference() {
            super(Index.class);
        }

        public IndexReference(ObjectReference table, String indexName) {
            super(Index.class, table, indexName);
        }

        public IndexReference(ObjectReference indexContainer, ObjectReference table, String indexName) {
            this(table, indexName);
            this.indexContainer = indexContainer;
        }

        public final ObjectReference getTable() {
            return container;
        }
    }

    public static class IndexedColumn extends AbstractTableObject {
        public Boolean computed;
        public Boolean descending;
        public Integer position;

        public IndexedColumn() {
        }

        public IndexedColumn(String name) {
            super(name);
        }

        public IndexedColumn(ObjectReference table, String name) {
            super(table, name);
        }

        public IndexedColumn(Column.ColumnReference column, Boolean descending) {
            this(column);
            this.descending = descending;
        }

        public IndexedColumn(Column.ColumnReference column) {
            super(column.getRelation(), column.name);
        }

        public String toString(boolean includeRelation) {
            if (includeRelation) {
                return toString();
            } else {
                return name  + (descending != null && descending ? " DESC" : "");
            }
        }

    }
}
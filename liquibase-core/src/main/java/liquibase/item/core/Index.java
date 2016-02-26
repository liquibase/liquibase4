package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.ItemReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Index extends AbstractRelationBasedObject<IndexReference> {

    public ItemReference indexSchema;
    public List<IndexedColumn> columns = new ArrayList<>();
    public String tablespace;
    public Boolean unique;
    public Boolean clustered;

    public enum IndexDirection {
        ASC,
        DESC,
    }

    public Index() {
    }

    public Index(String name) {
        super(Table.class, name);
    }

    public Index(String name, RelationReference table) {
        super(name, table);
    }

    public Index(String indexName, RelationReference table, IndexedColumn... columns) {
        this(indexName, table);

        if (columns != null && columns.length > 0) {
            this.columns.addAll(Arrays.asList(columns));
        }
    }

    public Index(String indexName, IndexedColumn... columns) {
        this(indexName, null, columns);
    }


    @Override
    public IndexReference toReference() {
        return new IndexReference(name, relation, indexSchema);
    }

    public static class IndexedColumn extends AbstractExtensibleObject {
        public String name;
        public Boolean computed;
        public IndexDirection direction;
        public Integer position;

        public IndexedColumn() {
        }

        public IndexedColumn(String name) {
            this.name = name;
        }

        public IndexedColumn(String name, IndexDirection direction) {
            this(name);
            this.direction = direction;
        }

        public String toString(boolean includeRelation) {
            if (includeRelation) {
                return toString();
            } else {
                return name + (direction == null ? "" : " " + direction);
            }
        }

    }
}
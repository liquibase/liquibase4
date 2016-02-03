package liquibase.structure.core;

import liquibase.AbstractExtensibleObject;
import liquibase.structure.AbstractObject;
import liquibase.structure.AbstractTableObject;
import liquibase.structure.ObjectReference;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKey extends AbstractTableObject {

    public List<PrimaryKeyColumn> columns = new ArrayList<>();
    public String tablespace;
    public Boolean clustered;

    public PrimaryKey() {
    }

    public PrimaryKey(String pkName) {
        super(pkName);
    }

    public PrimaryKey(PrimaryKey.PrimaryKeyReference reference) {
        super(reference);
    }

    public PrimaryKey(ObjectReference table, String name) {
        super(table, name);
    }

    public PrimaryKey(String pkName, ObjectReference table, String... columns) {
        super(table, pkName);

        for (String columnName : columns) {
            this.columns.add(new PrimaryKeyColumn(columnName));
        }
    }

    @Override
    public ObjectReference toReference() {
        return new PrimaryKeyReference(table, name);
    }

    public boolean containsColumn(Column column) {
        if (this.table != null && column.table != null && !column.table.equals(this.table, true)) {
            return false;
        }
        for (PrimaryKeyColumn ref : CollectionUtil.createIfNull(columns)) {
            if (ref.name.equals(column.name)) {
                return true;
            }
        }
        return false;
    }

    public static class PrimaryKeyColumn extends AbstractExtensibleObject {

        public String name;
        /**
         * Not normally included in a snapshot because the primary key does not actually have a direction. Supported when creating a primary key, however.
         */
        public Index.IndexDirection direction;

        public PrimaryKeyColumn() {
        }

        public PrimaryKeyColumn(String name) {
            this.name = name;
        }

        public PrimaryKeyColumn(String name, Index.IndexDirection direction) {
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

    /**
     * Object container is the constrained table ObjectReference
     */
    public static class PrimaryKeyReference extends ObjectReference {

        public PrimaryKeyReference() {
            super(PrimaryKey.class);
        }

        public PrimaryKeyReference(ObjectReference table, String pkName) {
            super(PrimaryKey.class, table, pkName);
        }

        public ObjectReference getTable() {
            return container;
        }
    }


}
package liquibase.structure.core;

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
    public ObjectReference backingIndex;
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
            this.columns.add(new PrimaryKeyColumn(new Column.ColumnReference(table, columnName)));
        }
    }

    @Override
    public String toString() {
        return getName() + " on "+table.toShortString()+"(" + StringUtils.join(this.columns, ",", new StringUtils.ToStringFormatter()) + ")";
    }

    @Override
    public ObjectReference toReference() {
        return new PrimaryKeyReference(table, name);
    }

    public boolean containsColumn(Column column) {
        for (PrimaryKeyColumn ref : CollectionUtil.createIfNull(columns)) {
            if (ref.toReference().equals(column.toReference(), true)) {
                return true;
            }
        }
        return false;
    }

    public static class PrimaryKeyColumn extends AbstractTableObject {
        public Boolean descending;
        public Integer position;

        public PrimaryKeyColumn() {
        }

        public PrimaryKeyColumn(Column.ColumnReference column) {
            super(column.container, column.name);
        }

        public PrimaryKeyColumn(Column.ColumnReference column, Boolean descending) {
            this(column);
            this.descending = descending;
        }

        public String toString(boolean includeRelation) {
            if (includeRelation) {
                return toString();
            } else {
                return name + (descending != null && descending ? " DESC" : "");
            }
        }

        @Override
        public String toString() {
            return name + (descending != null && descending ? " DESC" : "");
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
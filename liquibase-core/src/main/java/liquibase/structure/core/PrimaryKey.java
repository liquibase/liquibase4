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

    public PrimaryKey(ObjectReference reference) {
        super(reference);
    }

    public PrimaryKey(ObjectReference container, String name) {
        super(container, name);
    }

    public PrimaryKey(String pkName, ObjectReference table, String... columns) {
        super(table, pkName);
                
        for (String columnName : columns) {
            this.columns.add(new PrimaryKeyColumn(new ObjectReference(table, columnName)));
        }
    }

    @Override
    public String toString() {
        return getName() + "(" + StringUtils.join(this.columns, ",", new StringUtils.ToStringFormatter()) + ")";
    }

    public boolean containsColumn(Column column) {
        for (PrimaryKeyColumn ref : CollectionUtil.createIfNull(columns)) {
            if (name.equals(column.name)) {
                return true;
            }
        }
        return false;
    }

    public static class PrimaryKeyColumn extends AbstractTableObject {
        public Boolean descending;

        public PrimaryKeyColumn() {
        }

        public PrimaryKeyColumn(ObjectReference column) {
            super(column.container, column.name);
        }

        public PrimaryKeyColumn(ObjectReference column, Boolean descending) {
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

}
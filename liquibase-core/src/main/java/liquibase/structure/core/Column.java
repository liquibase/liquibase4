package liquibase.structure.core;

import liquibase.AbstractExtensibleObject;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.ObjectReference;

import java.math.BigInteger;

/**
 * Container is assumed to be the table
 */
public class Column extends AbstractDatabaseObject {

    public DataType type;
    public AutoIncrementInformation autoIncrementInformation;
    public Boolean nullable;
    public Object defaultValue;
    public String remarks;
    public Boolean virtual;

    public Column() {
    }

    public Column(String name) {
        super(name);
    }

    public Column(ObjectReference nameAndTable) {
        super(nameAndTable);
    }

    public Column(ObjectReference table, String name) {
        super(table, name);
    }

    public Column(ObjectReference table, String columnName, DataType type, Boolean nullable) {
        this(table, columnName);
        this.type = type;
        this.nullable = nullable;
    }

    public Column(ObjectReference table, String columnName, String type) {
        this(table, columnName, DataType.parse(type));
    }

    public Column(ObjectReference table, String columnName, DataType type) {
        this(table, columnName, type, null);
    }

    public Column setName(String name, boolean virtual) {
        this.name = name;
        this.virtual = virtual;

        return this;
    }

    public boolean isAutoIncrement() {
        return autoIncrementInformation != null;
    }

    public String toString(boolean includeRelation) {
        if (includeRelation) {
            return toString();
        } else {
            return name;
        }
    }

    @Override
    public int compareTo(Object other) {
        return this.getName().compareTo(((Column) other).getName());
    }

    /**
     * For a column reference, "container" is the table.
     */
    public static class ColumnReference extends ObjectReference {

        public ColumnReference() {
            type = Column.class;
        }

        public ColumnReference(ObjectReference table, String name) {
            super(Column.class, table, name);
        }

        public ColumnReference(String... names) {
            super(names);
            type = Column.class;
        }

        public ObjectReference getRelation() {
            return container;
        }

    }

    public static class AutoIncrementInformation extends AbstractExtensibleObject {
        public BigInteger startWith;
        public BigInteger incrementBy;

        public AutoIncrementInformation() {
        }

        public AutoIncrementInformation(Number startWith, Number incrementBy) {
            this.startWith = startWith == null ? null : BigInteger.valueOf(startWith.longValue());
            this.incrementBy = incrementBy == null ? null : BigInteger.valueOf(incrementBy.longValue());
        }

        @Override
        public String toString() {
            return "AUTO INCREMENT START WITH " + startWith + " INCREMENT BY " + incrementBy;
        }

    }
}


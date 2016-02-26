package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.datatype.DataType;

import java.math.BigInteger;

/**
 * A database column, which can be for either a {@link Table} or a {@link View}.
 */
public class Column extends AbstractRelationBasedObject<ColumnReference> {

    public DataType type;
    public AutoIncrementInformation autoIncrementInformation;
    public Boolean nullable;
    public Object defaultValue;
    public String remarks;

    /**
     * Set to true if the column is not a real column, but a computed value. Note: normal columns on a view are not "virtual", even though they are technically computed.
     */
    public Boolean virtual;

    public Column() {
    }

    public Column(String name) {
        super(Table.class, name);
    }

    public Column(String name, RelationReference relation) {
        super(name, relation);
    }

    public Column(String columnName, RelationReference relation, DataType type, Boolean nullable) {
        this(columnName, relation);
        this.type = type;
        this.nullable = nullable;
    }

    /**
     * Convenience method to check if this column has {@link #autoIncrementInformation} set.
     */
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
    public ColumnReference toReference() {
        return new ColumnReference(name, relation);
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

    public static class FunctionDefaultValue {

        private String value;

        public FunctionDefaultValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FunctionDefaultValue) {
                return this.toString().equals(obj.toString());
            } else {
                return super.equals(obj);
            }
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }
}


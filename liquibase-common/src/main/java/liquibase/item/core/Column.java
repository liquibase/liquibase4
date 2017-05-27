package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.item.AbstractItemPreprocessor;
import liquibase.parser.unprocessor.AbstractItemUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

import java.math.BigInteger;

/**
 * A database column, which can be for either a {@link Table} or a {@link View}.
 */
public class Column extends AbstractRelationBasedObject<ColumnReference> {

    public DataType type;
    public AutoIncrementDetails autoIncrementDetails;
    public Boolean nullable;
    public Object defaultValue;

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
     * Convenience method to check if this column has {@link #autoIncrementDetails} set.
     */
    public boolean isAutoIncrement() {
        return autoIncrementDetails != null;
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

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractItemPreprocessor(Column.class) {
            @Override
            protected void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException {
                itemNode.renameChildren("tableName", "relationName");
                this.convertToRelationReferenceNode("catalogName", "schemaName", "relationName", itemNode);
                itemNode.renameChildren("columnName", "name");
                convertValueOptions("defaultValue", itemNode);
                this.fixAutoIncrement(itemNode);
//                this.fixConstraints(itemNode, relation, mainNode);
                itemNode.renameChildren("computed", "virtual");

            }

            public void fixAutoIncrement(ParsedNode column) throws ParseException {
                ParsedNode autoIncrementNode = column.getChild("autoIncrement", false);
                if (autoIncrementNode != null) {
                    if (autoIncrementNode.getValue(false, Boolean.class)) {
                        autoIncrementNode.rename("autoIncrementDetails");
                        autoIncrementNode.setValue(null);
                    } else {
                        autoIncrementNode.remove();
                    }
                }
                autoIncrementNode = column.getChild("autoIncrementDetails", false);
                ParsedNode startWith = column.getChild("startWith", false);
                if (startWith != null) {
                    if (autoIncrementNode == null) {
                        throw new ParseException("Cannot specify 'startWith' without autoIncrement defined", startWith);
                    } else {
                        startWith.moveTo(autoIncrementNode);
                    }
                }

                ParsedNode incrementBy = column.getChild("incrementBy", false);
                if (incrementBy != null) {
                    if (autoIncrementNode == null) {
                        throw new ParseException("Cannot specify 'incrementBy' without autoIncrement defined", incrementBy);
                    } else {
                        incrementBy.moveTo(autoIncrementNode);
                    }
                }
            }
        };
    }


    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractItemUnprocessor(Column.class) {
            @Override
            protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException {
                Boolean virtual = typeNode.getChildValue("virtual", Boolean.class, false);
                if (virtual != null && !virtual) {
                    typeNode.removeChildren("virtual");
                }
            }
        };
    }


    public static class AutoIncrementDetails extends AbstractExtensibleObject {
        public BigInteger startWith;
        public BigInteger incrementBy;

        public AutoIncrementDetails() {
        }

        public AutoIncrementDetails(Number startWith, Number incrementBy) {
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


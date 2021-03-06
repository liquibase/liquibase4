package liquibase.item.datatype;

import liquibase.Scope;
import liquibase.item.FunctionCall;
import liquibase.plugin.AbstractPlugin;

public abstract class DataTypeLogic extends AbstractPlugin {

    public abstract int getPriority(DataType type, Scope scope);


    /**
     * Translate the given dataType into a string for inclusion in SQL.
     */
    public String toSql(DataType dataType, Scope scope) {
        if (dataType == null) {
            return null;
        }
        return dataType.toString();
    }

    /**
     * Translate the given value into a string for inclusion in SQL.
     */
    public String toSql(Object value, DataType dataType, Scope scope) {
        if (value == null) {
            return "NULL";
        }

        if (value instanceof FunctionCall) {
            return ((FunctionCall) value).value;
        }
        return scope.getDatabase().quoteString(value.toString(), scope);
    }

    /**
     * Translate the given dataType into a new DataType instance that is more database-independent
     */
    public DataType standardizeType(DataType dataType, Scope scope) {
        return (DataType) dataType.clone();
    }

}

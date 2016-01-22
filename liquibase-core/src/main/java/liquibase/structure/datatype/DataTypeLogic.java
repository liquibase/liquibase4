package liquibase.structure.datatype;

import liquibase.Scope;
import liquibase.servicelocator.Service;

public abstract class DataTypeLogic implements Service {

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
        return value.toString();
    }

    /**
     * Translate the given dataType into a new DataType instance that is more database-independent
     */
    public DataType standardizeType(DataType dataType, Scope scope) {
        return (DataType) dataType.clone();
    }

}

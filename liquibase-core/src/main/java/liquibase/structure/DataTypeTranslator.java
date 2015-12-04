package liquibase.structure;

import liquibase.Scope;
import liquibase.servicelocator.Service;
import liquibase.structure.core.DataType;

public abstract class DataTypeTranslator implements Service {

    public abstract int getPriority(Scope scope);


    /**
     * Translate the given dataType into a string for inclusion in SQL.
     */
    public String toSql(DataType dataType, Scope scope) {
        dataType = (DataType) dataType.clone();
        return dataType.toString();
    }

    /**
     * Translate the given dataType into a new DataType instance that is more database-independent
     */
    public DataType standardizeType(DataType dataType, Scope scope) {
        return (DataType) dataType.clone();
    }

}

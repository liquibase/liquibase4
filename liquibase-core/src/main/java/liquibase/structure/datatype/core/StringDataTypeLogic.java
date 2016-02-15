package liquibase.structure.datatype.core;

import liquibase.Scope;
import liquibase.structure.datatype.DataType;
import liquibase.structure.datatype.DataTypeLogic;

public class StringDataTypeLogic extends DataTypeLogic {

    @Override
    public int getPriority(DataType type, Scope scope) {
        return PRIORITY_SPECIALIZED;
    }

    @Override
    public String toSql(Object value, DataType dataType, Scope scope) {
        if (value == null) {
            return "NULL";
        }
        return scope.getDatabase().quoteString(value.toString(), scope);
    }
}

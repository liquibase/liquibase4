package liquibase.item.datatype.core;

import liquibase.Scope;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;

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

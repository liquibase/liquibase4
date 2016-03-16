package liquibase.item.datatype.core;

import liquibase.Scope;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;

public class StringDataTypeLogic extends DataTypeLogic {

    @Override
    public int getPriority(DataType type, Scope scope) {
        if (type != null && type.standardType != null && type.standardType.valueType != null && type.standardType.valueType.equals(String.class)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public String toSql(Object value, DataType dataType, Scope scope) {
        if (value == null) {
            return "NULL";
        }
        return scope.getDatabase().quoteString(value.toString(), scope);
    }
}

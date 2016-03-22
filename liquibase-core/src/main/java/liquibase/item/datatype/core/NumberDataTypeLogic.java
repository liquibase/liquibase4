package liquibase.item.datatype.core;

import liquibase.Scope;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;

/**
 * Standard {@link DataTypeLogic} for number-based data types.
 */
public class NumberDataTypeLogic extends DataTypeLogic {

    @Override
    public int getPriority(DataType type, Scope scope) {
        if (type != null && type.standardType != null && type.standardType.valueType != null && Number.class.isAssignableFrom(type.standardType.valueType)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public String toSql(Object value, DataType dataType, Scope scope) {
        if (value == null) {
            return "NULL";
        }
        return value.toString();
    }
}

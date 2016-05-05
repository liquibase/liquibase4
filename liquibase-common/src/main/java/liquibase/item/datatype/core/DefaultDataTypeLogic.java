package liquibase.item.datatype.core;

import liquibase.Scope;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;

public class DefaultDataTypeLogic extends DataTypeLogic {

    @Override
    public int getPriority(DataType type, Scope scope) {
        return PRIORITY_DEFAULT;
    }

}

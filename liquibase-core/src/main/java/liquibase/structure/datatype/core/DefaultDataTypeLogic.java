package liquibase.structure.datatype.core;

import liquibase.Scope;
import liquibase.structure.datatype.DataType;
import liquibase.structure.datatype.DataTypeLogic;

public class DefaultDataTypeLogic extends DataTypeLogic {

    @Override
    public int getPriority(DataType type, Scope scope) {
        return PRIORITY_DEFAULT;
    }

}

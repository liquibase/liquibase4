package liquibase.structure;

import liquibase.Scope;
import liquibase.servicelocator.Service;
import liquibase.structure.core.DataType;

public class DefaultDataTypeTranslator extends DataTypeTranslator {

    public int getPriority(Scope scope) {
        return PRIORITY_DEFAULT;
    }

}

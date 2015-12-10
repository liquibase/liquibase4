package liquibase.structure.datatype;

import liquibase.Scope;
import liquibase.servicelocator.AbstractServiceFactory;

public class DataTypeLogicFactory extends AbstractServiceFactory<DataTypeLogic> {

    /**
     * Constructor is protected because it should be used as a singleton.
     */
    protected DataTypeLogicFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<DataTypeLogic> getServiceClass() {
        return DataTypeLogic.class;
    }

    @Override
    protected int getPriority(DataTypeLogic obj, Scope scope, Object... args) {
        DataType type = (DataType) args[0];
        return obj.getPriority(type, scope);
    }

    public DataTypeLogic getDataTypeLogic(DataType type, Scope scope) {
        return this.getService(scope, type);
    }
}

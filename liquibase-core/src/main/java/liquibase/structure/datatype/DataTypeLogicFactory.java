package liquibase.structure.datatype;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

public class DataTypeLogicFactory extends AbstractPluginFactory<DataTypeLogic> {

    /**
     * Constructor is protected because it should be used as a singleton.
     */
    protected DataTypeLogicFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<DataTypeLogic> getPluginClass() {
        return DataTypeLogic.class;
    }

    @Override
    protected int getPriority(DataTypeLogic obj, Scope scope, Object... args) {
        DataType type = (DataType) args[0];
        return obj.getPriority(type, scope);
    }

    public DataTypeLogic getDataTypeLogic(DataType type, Scope scope) {
        return this.getPlugin(scope, type);
    }
}

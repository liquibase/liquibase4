package liquibase.structure;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.plugin.AbstractPluginFactory;

public class TestObjectReferenceSupplierFactory extends AbstractPluginFactory<TestObjectReferenceSupplier> implements SingletonObject {

    protected TestObjectReferenceSupplierFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<TestObjectReferenceSupplier> getPluginClass() {
        return TestObjectReferenceSupplier.class;
    }

    @Override
    protected int getPriority(TestObjectReferenceSupplier obj, Scope scope, Object... args) {
        Class<? extends LiquibaseObject> objectType = (Class) args[0];
        return obj.getPriority(objectType, scope);
    }

    public <T extends LiquibaseObject> TestObjectReferenceSupplier<T> getObjectReferenceSupplier(Class<T> objectType, Scope scope) {
        return getPlugin(scope, objectType);
    }
}

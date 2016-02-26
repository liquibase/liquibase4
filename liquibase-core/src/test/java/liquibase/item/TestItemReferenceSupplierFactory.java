package liquibase.item;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.plugin.AbstractPluginFactory;

public class TestItemReferenceSupplierFactory extends AbstractPluginFactory<TestItemReferenceSupplier> implements SingletonObject {

    protected TestItemReferenceSupplierFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<TestItemReferenceSupplier> getPluginClass() {
        return TestItemReferenceSupplier.class;
    }

    @Override
    protected int getPriority(TestItemReferenceSupplier obj, Scope scope, Object... args) {
        Class<? extends Item> objectType = (Class) args[0];
        return obj.getPriority(objectType, scope);
    }

    public <T extends Item> TestItemReferenceSupplier<T> getItemReferenceSupplier(Class<T> objectType, Scope scope) {
        return getPlugin(scope, objectType);
    }
}

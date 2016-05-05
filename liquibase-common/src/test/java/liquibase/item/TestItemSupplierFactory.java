package liquibase.item;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.plugin.AbstractPluginFactory;

public class TestItemSupplierFactory extends AbstractPluginFactory<TestItemSupplier> implements SingletonObject {

    protected TestItemSupplierFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<TestItemSupplier> getPluginClass() {
        return TestItemSupplier.class;
    }

    @Override
    protected int getPriority(TestItemSupplier obj, Scope scope, Object... args) {
        Class<? extends Item> objectType = (Class) args[0];
        return obj.getPriority(objectType, scope);
    }

    public <T extends Item> TestItemSupplier<T> getItemSupplier(Class<T> objectType, Scope scope) {
        return getPlugin(scope, objectType);
    }
}

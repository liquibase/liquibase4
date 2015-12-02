package liquibase.structure;

import liquibase.Scope;
import liquibase.servicelocator.AbstractServiceFactory;

public class TestStructureSupplierFactory extends AbstractServiceFactory<TestStructureSupplier> {

    public TestStructureSupplierFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<TestStructureSupplier> getServiceClass() {
        return TestStructureSupplier.class;
    }

    @Override
    protected int getPriority(TestStructureSupplier obj, Scope scope, Object... args) {
        Class<? extends DatabaseObject> objectType = (Class) args[0];
        return obj.getPriority(objectType, scope);
    }

    public <T extends DatabaseObject> TestStructureSupplier<T> getStructureSupplier(Class<T> objectType, Scope scope) {
        return getService(scope, objectType);
    }
}

package liquibase.structure;

import liquibase.Scope;
import liquibase.servicelocator.AbstractServiceFactory;

public class TestStructureSupplierFactory extends AbstractServiceFactory<TestObjectReferenceSupplier> {

    public TestStructureSupplierFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<TestObjectReferenceSupplier> getServiceClass() {
        return TestObjectReferenceSupplier.class;
    }

    @Override
    protected int getPriority(TestObjectReferenceSupplier obj, Scope scope, Object... args) {
        Class<? extends LiquibaseObject> objectType = (Class) args[0];
        return obj.getPriority(objectType, scope);
    }

    public <T extends LiquibaseObject> TestObjectReferenceSupplier<T> getStructureSupplier(Class<T> objectType, Scope scope) {
        return getService(scope, objectType);
    }
}

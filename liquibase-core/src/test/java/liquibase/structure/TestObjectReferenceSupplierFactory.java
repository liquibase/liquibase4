package liquibase.structure;

import liquibase.Scope;
import liquibase.SingletonService;
import liquibase.servicelocator.AbstractServiceFactory;

public class TestObjectReferenceSupplierFactory extends AbstractServiceFactory<TestObjectReferenceSupplier> implements SingletonService {

    protected TestObjectReferenceSupplierFactory(Scope scope) {
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

    public <T extends LiquibaseObject> TestObjectReferenceSupplier<T> getObjectReferenceSupplier(Class<T> objectType, Scope scope) {
        return getService(scope, objectType);
    }
}

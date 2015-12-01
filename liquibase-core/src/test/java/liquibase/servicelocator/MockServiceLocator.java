package liquibase.servicelocator;

import liquibase.JUnitScope;

import java.util.HashSet;
import java.util.Set;

public class MockServiceLocator extends ServiceLocator {

    private Set<Object> services = new HashSet<>();

    public MockServiceLocator() {
        super(JUnitScope.getInstance());
    }

    @Override
    public <T> Set<T> findAllServices(Class<T> requiredInterface) {
        Set<T> returnSet = new HashSet<>();
        for (Object object : services) {
            if (requiredInterface.isAssignableFrom(object.getClass())) {
                returnSet.add((T) object);
            }
        }
        return returnSet;
    }

    public MockServiceLocator addService(Object service) {
        services.add(service);
        return this;
    }

    public MockServiceLocator removeService(Object service) {
        services.remove(service);
        return this;
    }

    public MockServiceLocator clearServices() {
        this.services.clear();
        return this;
    }
}

package liquibase.servicelocator;

import liquibase.Scope;

import java.util.*;

public class ServiceLocator {


    protected ServiceLocator(Scope scope) {
    }

    public <T> Set<T> findAllServices(Class<T> requiredInterface) {
        Iterator<T> iterator = ServiceLoader.load(requiredInterface).iterator();
        Set<T> returnList = new HashSet<>();
        while (iterator.hasNext()) {
            returnList.add(iterator.next());
        }
        return Collections.unmodifiableSet(returnList);
    }

}

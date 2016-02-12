package liquibase.servicelocator;

import liquibase.Scope;
import liquibase.SingletonService;

import java.util.*;

public class ServiceLocator implements SingletonService {

    private Map<Class, Set> services = new HashMap<>();

    protected ServiceLocator(Scope scope) {
    }

    public <T> Set<T> findAllServices(Class<T> requiredInterface) {
        Set<T> returnSet = services.get(requiredInterface);
        if (returnSet == null) {
            Iterator<T> iterator = ServiceLoader.load(requiredInterface).iterator();
            Set<T> returnList = new HashSet<>();
            while (iterator.hasNext()) {
                returnList.add(iterator.next());
            }
            returnSet = Collections.unmodifiableSet(returnList);
            services.put(requiredInterface, returnSet);
        }

        return returnSet;
    }

}

package liquibase.test

import liquibase.ExtensibleObject
import liquibase.SingletonService
import liquibase.util.CollectionUtil

public class TestObjectFactory implements SingletonService {

    protected TestObjectFactory() {
    }

    public <T extends ExtensibleObject> List<T> createAllPermutationsWithoutNulls(Class<T> type, Map<String, List<Object>> defaultValues) throws Exception {
        return createAllPermutations(type, defaultValues, false)
    }

    public <T extends ExtensibleObject> List<T> createAllPermutations(Class<T> type, Map<String, List<Object>> defaultValues, boolean addNulls = true) throws Exception {
        List<T> returnList = new ArrayList<>();
        for (Map<String, Object> parameterValues : CollectionUtil.permutations(defaultValues, addNulls)) {
            T obj = type.newInstance();
            for (Map.Entry<String, ?> entry : parameterValues.entrySet()) {
                if (!obj.getStandardAttributeNames().contains(entry.getKey())) {
                    throw new RuntimeException("No attribute "+entry.getKey()+" on "+type.getName())
                }
                obj.set(entry.getKey(), entry.getValue());
            }
            returnList.add(obj);
        }

        return returnList;
    }
}

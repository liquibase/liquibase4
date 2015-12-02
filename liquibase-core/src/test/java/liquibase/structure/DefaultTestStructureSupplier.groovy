package liquibase.structure

import liquibase.Scope
import liquibase.snapshot.Snapshot

public class DefaultTestStructureSupplier<T extends DatabaseObject> extends TestStructureSupplier<T> {

    int getPriority(Class<? extends DatabaseObject> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }


    @Override
    public List<? extends DatabaseObject> getTestObjects(Class type, Snapshot snapshot, Scope scope) {
        List<? extends DatabaseObject> returnList = new ArrayList<>();

        for (def name : getObjectNames(scope)) {
            def instance = type.newInstance()
            instance.name = name
            returnList.add(instance)
        }

        return returnList;
    }

    @Override
    Set<Class<? extends DatabaseObject>> requires(Scope scope) {
        return null
    }
}

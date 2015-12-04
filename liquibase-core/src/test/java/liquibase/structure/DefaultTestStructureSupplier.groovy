package liquibase.structure

import liquibase.Scope
import liquibase.snapshot.Snapshot

public class DefaultTestStructureSupplier<T extends LiquibaseObject> extends TestStructureSupplier<T> {

    int getPriority(Class<? extends LiquibaseObject> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }


    @Override
    public List<? extends LiquibaseObject> getTestObjects(Class type, Snapshot snapshot, Scope scope) {
        List<? extends LiquibaseObject> returnList = new ArrayList<>();

        for (def name : getObjectNames(scope)) {
            def instance = type.newInstance()
            instance.name = name
            returnList.add(instance)
        }

        return returnList;
    }

    @Override
    Set<Class<? extends LiquibaseObject>> requires(Scope scope) {
        return null
    }
}

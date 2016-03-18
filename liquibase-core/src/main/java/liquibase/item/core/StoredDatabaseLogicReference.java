package liquibase.item.core;

import liquibase.item.DatabaseObjectReference;
import liquibase.item.ItemReference;

public class StoredDatabaseLogicReference extends DatabaseObjectReference {

    public StoredDatabaseLogicReference() {
    }

    public StoredDatabaseLogicReference(Class<? extends StoredDatabaseLogic> type, String name, ItemReference container) {
        super(type, name, container);
    }

    public StoredDatabaseLogicReference(Class<? extends StoredDatabaseLogic> type, String... names) {
        super(type, names);
    }
}

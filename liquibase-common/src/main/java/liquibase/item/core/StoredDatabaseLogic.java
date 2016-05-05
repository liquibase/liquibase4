package liquibase.item.core;

import liquibase.item.AbstractDatabaseObject;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.ItemReference;

/**
 * Base class for all database-based logic, such as stored procedure, trigger, etc.
 */
public abstract class StoredDatabaseLogic extends AbstractDatabaseObject<StoredDatabaseLogicReference> {

    public StoredDatabaseLogic() {
    }

    public StoredDatabaseLogic(String name) {
        super(name);
    }

    public StoredDatabaseLogic(String name, DatabaseObjectReference container) {
        super(name);
        this.container = container;
    }

    /**
     * Container for the logic. Not typed because it can be a schema, schema, table, or other objects depending on the logic.
     */
    public DatabaseObjectReference container;

    public Boolean valid;

    /**
     * The text/body of the object. Should contain the entire "create" SQL, including comments.
     */
    public String body;

    @Override
    public StoredDatabaseLogicReference toReference() {
        return new StoredDatabaseLogicReference(getClass(), name, container);
    }
}

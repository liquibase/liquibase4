package liquibase.item.core;

import liquibase.item.DatabaseObjectReference;

/**
 * A stored procedure.
 * Functions, triggers, etc. should be handled by other subclasses of {@link StoredDatabaseLogic}, but these are not supported by Liquibase core.
 */
public class StoredProcedure extends StoredDatabaseLogic {

    public StoredProcedure() {
    }

    public StoredProcedure(String name) {
        super(name);
    }

    public StoredProcedure(String name, DatabaseObjectReference container) {
        super(name, container);
    }
}

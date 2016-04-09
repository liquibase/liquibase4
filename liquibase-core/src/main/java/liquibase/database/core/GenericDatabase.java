package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;

/**
 * Fallback database when no others match. Follows SQL standard.
 */
public class GenericDatabase extends AbstractJdbcDatabase {

    public GenericDatabase() {
    }

    public GenericDatabase(DatabaseConnection connection, Scope scope) {
        setConnection(connection, scope);
    }

    @Override
    public int getPriority(Scope scope) {
        return 0;
    }

    @Override
    public boolean supports(DatabaseConnection conn, Scope scope) {
        return false;
    }

    @Override
    public String getShortName() {
        return "generic";
    }
}

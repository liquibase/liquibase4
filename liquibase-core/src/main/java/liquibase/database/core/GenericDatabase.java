package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;

/**
 * Fallback database when no others match.
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
    public boolean supports(DatabaseConnection conn, Scope scope) throws DatabaseException {
        return false;
    }

    @Override
    public String getDefaultDriver(String url, Scope scope) {
        return null;
    }    

    @Override
    public String getShortName() {
        return "generic";
    }


    @Override
    public String getCurrentDateTimeFunction(Scope scope) {
        return currentDateTimeFunction;
    }

}

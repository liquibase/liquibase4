package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;

/**
 * Fallback database when no others match. Should follow SQL standard as closely as possible.
 * SQL reference: http://savage.net.au/SQL/sql-2003-2.bnf.html
 * Database feature comparison: http://www.sql-workbench.net/dbms_comparison.html
 */
public class UnsupportedDatabase extends AbstractJdbcDatabase {

    public UnsupportedDatabase() {
    }

    public UnsupportedDatabase(DatabaseConnection connection) {
        setConnection(connection);
    }

    @Override
    public int getPriority(Scope scope) {
        return 0;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        super.setConnection(conn);
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return false;
    }

    @Override
    public String getDefaultDriver(String url) {
        return null;
    }    

    @Override
    public String getShortName() {
        return "unsupported";
    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Unsupported";
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return currentDateTimeFunction;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

}

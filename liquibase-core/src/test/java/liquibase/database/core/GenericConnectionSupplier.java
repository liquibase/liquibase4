package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.ConnectionSupplier;
import liquibase.database.DatabaseConnection;
import liquibase.database.MockJdbcConnection;
import testmd.logic.SetupResult;

public class GenericConnectionSupplier extends ConnectionSupplier {

    @Override
    protected String getDatabaseShortName() {
        return "generic";
    }

    @Override
    public String createJdbcUrl() {
        return "jdbc:generic";
    }

    @Override
    public DatabaseConnection getConnection(Scope scope) throws SetupResult {
        return new MockJdbcConnection();
    }
}

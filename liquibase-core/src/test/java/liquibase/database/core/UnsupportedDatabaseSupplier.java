package liquibase.database.core;

import liquibase.database.ConnectionSupplier;
import liquibase.database.DatabaseConnection;
import liquibase.database.MockJdbcConnection;
import testmd.logic.SetupResult;

public class UnsupportedDatabaseSupplier extends ConnectionSupplier {

    @Override
    public String getDatabaseShortName() {
        return "unsupported";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:unsupported";
    }

    @Override
    public DatabaseConnection getConnection() throws SetupResult {
        return new MockJdbcConnection();
    }
}

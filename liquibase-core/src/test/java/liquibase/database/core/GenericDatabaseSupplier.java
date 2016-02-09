package liquibase.database.core;

import liquibase.database.ConnectionSupplier;
import liquibase.database.DatabaseConnection;
import liquibase.database.MockJdbcConnection;
import testmd.logic.SetupResult;

public class GenericDatabaseSupplier extends ConnectionSupplier {

    @Override
    public String getDatabaseShortName() {
        return "generic";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:generic";
    }

    @Override
    public DatabaseConnection getConnection() throws SetupResult {
        return new MockJdbcConnection();
    }
}

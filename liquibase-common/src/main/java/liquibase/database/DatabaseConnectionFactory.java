package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.resource.InputStreamList;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.ServiceLoader;

public class DatabaseConnectionFactory extends AbstractPluginFactory<DatabaseConnection> {


    protected DatabaseConnectionFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<DatabaseConnection> getPluginClass() {
        return DatabaseConnection.class;
    }


    @Override
    protected int getPriority(DatabaseConnection obj, Scope scope, Object... args) {
        return obj.getPriority((DatabaseConnection.ConnectionParameters) args[0], scope);
    }

    public DatabaseConnection connect(DatabaseConnection.ConnectionParameters connectionParameters, Scope scope) throws DatabaseException {
        DatabaseConnection connection = getPlugin(scope, connectionParameters);
        if (connection == null) {
            throw new DatabaseException("No connection logic found for "+connectionParameters.describe());
        }

        connection = (DatabaseConnection) connection.clone();
        connection.openConnection(connectionParameters, scope);

        return connection;
    }
}

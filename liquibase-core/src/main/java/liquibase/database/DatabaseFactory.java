package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.plugin.Plugin;
import liquibase.resource.InputStreamList;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.Snapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * PluginFactory to create the correct Database implementation based on available extensions.
 */
public class DatabaseFactory extends AbstractPluginFactory<Database> {
    private Logger log;

    protected DatabaseFactory(Scope scope) {
        super(scope);
        log = LoggerFactory.getLogger(DatabaseFactory.class);
    }

    @Override
    protected Class<Database> getPluginClass() {
        return Database.class;
    }

    /**
     * Takes either a {@link DatabaseConnection} as the first arg or a Database shortName.
     */
    @Override
    protected int getPriority(Database obj, Scope scope, Object... args) {
        if (args[0] instanceof String) {
            String databaseName = (String) args[0];

            if (obj.getShortName().equals(databaseName)) {
                return obj.getPriority(scope);
            } else {
                return Plugin.PRIORITY_NOT_APPLICABLE;
            }
        } else if (args[0] instanceof DatabaseConnection) {
            DatabaseConnection connection = (DatabaseConnection) args[0];
            if (connection instanceof OfflineConnection) {
                if (((OfflineConnection) connection).supports(obj, scope)) {
                    return obj.getPriority(scope);
                } else {
                    return Plugin.PRIORITY_NOT_APPLICABLE;
                }
            } else {
                if (obj.supports(connection, scope)) {
                    return obj.getPriority(scope);
                } else {
                    return Plugin.PRIORITY_NOT_APPLICABLE;
                }
            }
        } else {
            throw new UnexpectedLiquibaseException("Unknown argument type: " + args[0].getClass());
        }
    }


    /**
     * Returns the correct {@link Database} implementation for the passed connection. Each call will return a new Database instance.
     * The returned database has {@link Database#setConnection(DatabaseConnection, Scope)} configured and {@link DatabaseConnection#configureDatabase(Database, Scope)} called.
     */
    public Database getDatabase(DatabaseConnection connection, Scope scope) throws DatabaseException {
        Database database = getPlugin(scope, connection);
        try {
            database = database.getClass().newInstance();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }

        database.setConnection(connection, scope);
        connection.configureDatabase(database, scope);

        return database;
    }


    public Database connect(String url,
                            String username,
                            String password,
                            String driverClass,
                            String databaseClass,
                            String driverPropertiesClass,
                            String additionalDriverPropertiesPath,
                            ResourceAccessor resourceAccessor,
                            Scope scope) throws DatabaseException {
        DatabaseConnection connection = null;
        try {
            if (url.startsWith("offline:")) {
                connection = new OfflineConnection(url, resourceAccessor);
            } else {

               Properties driverProperties;
                if (driverPropertiesClass == null) {
                    driverProperties = new Properties();
                } else {
                    driverProperties = (Properties) Class.forName(driverPropertiesClass, true, scope.getClassLoader(true)).newInstance();
                }

                if (username != null) {
                    driverProperties.put("user", username);
                }
                if (password != null) {
                    driverProperties.put("password", password);
                }
                if (additionalDriverPropertiesPath != null) {
                    try(InputStreamList propertiesFiles = resourceAccessor.openStreams(additionalDriverPropertiesPath)) {
                        if (propertiesFiles == null) {
                            throw new UnexpectedLiquibaseException("Can't open properties from the path: '" + additionalDriverPropertiesPath + "'");
                        } else {
                            for (InputStream stream : propertiesFiles) {
                                driverProperties.load(stream);
                            }
                        }
                    }
                }

                Connection jdbcConnection;
                try {
                    if (driverClass == null) {
                        jdbcConnection =  DriverManager.getConnection(url, driverProperties);
                    } else {
                        Driver driver = (Driver) Class.forName(driverClass, true, scope.getClassLoader(true)).newInstance();
                        jdbcConnection = driver.connect(url, driverProperties);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Cannot find database driver: " + e.getMessage());
                }

                LoggerFactory.getLogger(getClass()).debug("Connection to " + url + " successful");

                connection = new JdbcConnection(jdbcConnection);
            }

            if (databaseClass == null) {
                return getDatabase(connection, scope);
            } else {
                Database database = (Database) Class.forName(databaseClass, true, scope.getClassLoader(true)).newInstance();
                database.setConnection(connection, scope);
                connection.configureDatabase(database, scope);

                return database;
            }
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    public Database getDatabase(String shortName) {
        return getPlugin(getFactoryScope(), shortName);
    }

    public Database getDatabaseForUrl(String url) {
        return getPlugin(getFactoryScope(), url);
    }

    /**
     * Creates a new Database instance with an offline connection pointing to the given snapshot
     */
    public Database fromSnapshot(Snapshot snapshot, Scope scope) {
        Database database = snapshot.getScope().getDatabase();

        DatabaseConnection conn = new OfflineConnection("offline:" + database.getShortName(), snapshot, snapshot.getScope().getResourceAccessor());

        try {
            return getDatabase(conn, scope);
        } catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

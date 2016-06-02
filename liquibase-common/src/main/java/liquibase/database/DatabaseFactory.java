package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.plugin.Plugin;
import liquibase.snapshot.Snapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public Database connect(DatabaseConnection.ConnectionParameters connectionParameters,
                            String databaseClass,
                            Scope scope) throws DatabaseException {
        try {
            DatabaseConnection connection = scope.getSingleton(DatabaseConnectionFactory.class).connect(connectionParameters, scope);

            if (databaseClass == null) {
                return getDatabase(connection, scope);
            } else {
                Database database = (Database) Class.forName(databaseClass, true, scope.getClassLoader(true)).newInstance();
                database.setConnection(connection, scope);
                connection.configureDatabase(database, scope);

                return database;
            }
        } catch (DatabaseException e) {
            throw e;
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
    public Database fromSnapshot(Snapshot snapshot, Scope scope)  {
        Database database = snapshot.getScopeCreatedUnder().getDatabase();

        DatabaseConnection.ConnectionParameters parameters = new DatabaseConnection.ConnectionParameters();
        parameters.url = "offline:" + database.getShortName();
        parameters.set(OfflineConnection.OfflineConnectionParameters.snapshot.name(), snapshot);
        try {
            return connect(parameters, null, snapshot.getScopeCreatedUnder());
        } catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

package liquibase.database;

import liquibase.AbstractExtensibleObject;
import liquibase.JUnitScope;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.database.core.GenericDatabase;
import liquibase.exception.DatabaseException;
import liquibase.item.core.CatalogReference;
import liquibase.plugin.AbstractPlugin;
import liquibase.plugin.Plugin;
import liquibase.item.core.Catalog;
import liquibase.item.core.Schema;
import liquibase.item.core.SchemaReference;
import org.slf4j.LoggerFactory;
import testmd.logic.SetupResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used by tests to create connections to available databases.
 */
public abstract class ConnectionSupplier extends AbstractPlugin {

    public String primaryCatalog = "LBCAT";
    public String primarySchema = "LBSCHEMA";
    public String username = "lbuser";
    public String password = "lbuser";

    public String alternateCatalog = "LBCAT2";
    public String alternateSchema = "LBSCHEMA2";
    public String alternateUsername = "lbuser2";
    public String alternatePassword = "lbuser2";

    public String alternateTablespace = "lbtbsp2";

    public String host = "vagrant";

    private DatabaseConnection connection;
    private SetupResult connectionResult;

    /**
     * A descriptive name the type of connection this class creates. Default implementation returns "standard"
     */
    public String getName() {
        return "standard";
    }

    /**
     * Returns the {@link Database#getShortName()} which should be used by {@link #getDatabase()} when creating database objects.
     */
    protected abstract String getDatabaseShortName();

    /**
     * Returns the database to use for connections from this supplier.
     * Default implementation finds database that matches {@link #getDatabaseShortName()}
     */
    public Database getDatabase() {
        return JUnitScope.getInstance().getSingleton(DatabaseFactory.class).getDatabase(getDatabaseShortName());
    }


    /**
     * Creates the URL for opening the connection.
     */
    public abstract String createJdbcUrl();

    @Override
    public String toString() {
        return getDatabaseShortName() + " " + getName();
    }

    /**
     * Opens a connection, attaches it to a {@link Database} and returns a new {@link Scope} including the database.
     */
    public Scope connect(Scope scope) throws DatabaseException {
        DatabaseConnection databaseConnection = getConnection(scope);
        Database db = this.getDatabase();
        if (!(db instanceof GenericDatabase) && !db.supports(databaseConnection, scope)) {
            throw new DatabaseException("Incorrect db '" + db.getShortName() + "' for connection " + databaseConnection.getURL());
        }
        db.setConnection(databaseConnection, scope);

        return scope.child(JUnitScope.Attr.connectionSupplier, this)
                .child(Scope.Attr.database, db);
    }

    /**
     * Return the existing open connection or opens a new one if not yet open.
     */
    protected DatabaseConnection getConnection(Scope scope) throws SetupResult {
        if (connection == null && connectionResult == null) {
            LoggerFactory.getLogger(getClass()).info("Opening connection as " + username + " to " + this.createJdbcUrl());
            try {
                Connection dbConn = DriverManager.getConnection(this.createJdbcUrl(), username, password);
                connection = initConnection(new JdbcConnection(dbConn), scope);

                if (connection == null) {
                    connectionResult = new SetupResult.CannotVerify(getClass().getName() + ".initConnection returned a null connection");
                }
            } catch (Exception e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                connectionResult = new SetupResult.CannotVerify("Cannot open connection: " + e.getMessage());
            }
        }
        if (connection != null) {
            return connection;
        } else {
            throw connectionResult;
        }
    }

    /**
     * Called by {@link #getConnection(Scope)} to perform any necessary initialization on a newly opened connection.
     * Return the connection to store for future calls to {@link #getConnection(Scope)} or null if the connection cannot be used.
     */
    protected DatabaseConnection initConnection(DatabaseConnection connection, Scope scope) throws DatabaseException {
        return connection;
    }

    /**
     * Returns all schemas available to this connection. Does not not interact with an actual connection, but returns a pre-defined list that the connection will need to support.
     */
    public List<SchemaReference> getAllSchemas() {
        if (!getDatabase().supports(Schema.class, JUnitScope.getInstance())) {
            return new ArrayList<>();
        } else {
            List<CatalogReference> catalogs = getAllCatalogs();
            if (catalogs.size() == 0) {
                return Arrays.asList(new SchemaReference(primarySchema), new SchemaReference(alternateSchema));
            } else {
                List<SchemaReference> schemas = new ArrayList<>();
                for (CatalogReference catalog : catalogs) {
                    schemas.add(new SchemaReference(primarySchema, catalog));
                    schemas.add(new SchemaReference(alternateSchema, catalog));
                }
                return schemas;
            }
        }
    }

    /**
     * Returns all catalogs available to this connection. Does not not interact with an actual connection, but returns a pre-defined list that the connection will need to support.
     */
    public List<CatalogReference> getAllCatalogs() {
        if (getDatabase().supports(Catalog.class, JUnitScope.getInstance())) {
            return Arrays.asList(new CatalogReference(primaryCatalog),
                    new CatalogReference(alternateCatalog));

        } else {
            return new ArrayList<>();
        }
    }
}

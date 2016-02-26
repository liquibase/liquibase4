package liquibase.database;

import liquibase.JUnitScope;
import liquibase.Scope;
import liquibase.database.core.GenericDatabase;
import liquibase.exception.DatabaseException;
import liquibase.plugin.Plugin;
import liquibase.item.Item;
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

public abstract class ConnectionSupplier implements Cloneable, Plugin {

    public static final String CONFIG_NAME_STANDARD = "standard";

    private String version;
    private String ipAddress = "vagrant";
    private DatabaseConnection connection;
    private SetupResult connectionResult;

    public abstract String getDatabaseShortName();

    public String getConfigurationName() {
        return CONFIG_NAME_STANDARD;
    }

    public abstract String getJdbcUrl();

    public String getPrimaryCatalog() {
        return "LBCAT";
    }

    public String getPrimarySchema() {
        return "LBSCHEMA";
    }

    public String getDatabaseUsername() {
        return "lbuser";
    }

    public String getDatabasePassword() {
        return "lbuser";
    }

    public String getAlternateUsername() {
        return "lbuser2";
    }

    public String getAlternateUserPassword() {
        return "lbuser2";
    }

    public String getAlternateCatalog() {
        return "LBCAT2";
    }

    public String getAlternateSchema() {
        return "LBSCHEMA2";
    }

    public String getAlternateTablespace() {
        return "lbtbsp2";
    }

    public String getAdminUsername() {
        return "lbadmin";
    }

    ;

    public String getAdminPassword() {
        return "lbadmin";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVersion() {
        return version;
    }

    public String getShortVersion() {
        if (getVersion() == null) {
            return "LATEST";
        }
        String[] split = getVersion().split("\\.");
        if (split.length == 1) {
            return split[0];
        } else {
            return split[0] + "." + split[1];
        }
    }


    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return getDatabaseShortName() + "[config:" + getConfigurationName() + "]";
    }

    public String getDescription() {
        String version = getVersion();
        if (version == null) {
            version = "LATEST";
        }

        return "JDBC Url: " + getJdbcUrl() + "\n" +
                "Version: " + version + "\n" +
                "Standard User: " + getDatabaseUsername() + "\n" +
                "         Password: " + getDatabasePassword() + "\n" +
                "Primary Catalog: " + getPrimaryCatalog() + "\n" +
                "Primary Schema: " + getPrimarySchema() + " (if applicable)\n" +
                "\n" +
                "Alternate User: " + getAlternateUsername() + "\n" +
                "          Password: " + getAlternateUserPassword() + "\n" +
                "Alternate Catalog: " + getAlternateCatalog() + "\n" +
                "Alternate Schema: " + getAlternateSchema() + " (if applicable)\n" +
                "Alternate Tablespace: " + getAlternateTablespace() + "\n";
    }

    protected DatabaseConnection getConnection(Scope scope) throws SetupResult {
        if (connection == null && connectionResult == null) {
            LoggerFactory.getLogger(getClass()).info("Opening connection as " + this.getDatabaseUsername() + " to " + this.getJdbcUrl());
            try {
                Connection dbConn = DriverManager.getConnection(this.getJdbcUrl(), this.getDatabaseUsername(), this.getDatabasePassword());
                connection = new JdbcConnection(dbConn);

                Database initDb = JUnitScope.getInstance().getSingleton(DatabaseFactory.class).getDatabase(connection, scope);
                initDb.setConnection(connection, scope);
                initConnection(JUnitScope.getInstance(initDb));
            } catch (Exception e) {
                connectionResult = new SetupResult.CannotVerify("Cannot open connection: " + e.getMessage());
            }
        }
        if (connection != null) {
            return connection;
        } else {
            throw connectionResult;
        }
    }

    protected void initConnection(Scope scope) {

    }

    public Database getDatabase() {
        return JUnitScope.getInstance().getSingleton(DatabaseFactory.class).getDatabase(getDatabaseShortName());
    }

    public Scope connect(Scope scope) throws DatabaseException {
        DatabaseConnection databaseConnection = getConnection(scope);
        Database db = scope.getDatabase();
        if (!(db instanceof GenericDatabase) && !db.supports(databaseConnection, scope)) {
            throw new DatabaseException("Incorrect db '" + db.getShortName() + "' for connection " + databaseConnection.getURL());
        }
        db.setConnection(databaseConnection, scope);

        return scope;
    }

    public List<SchemaReference> getAllSchemas() {
        if (getDatabase().supports(Catalog.class, JUnitScope.getInstance())) {
            return Arrays.asList(new SchemaReference(getPrimaryCatalog(), getPrimarySchema()),
                    new SchemaReference(getPrimaryCatalog(), getAlternateSchema()),
                    new SchemaReference(getAlternateCatalog(), getPrimarySchema()),
                    new SchemaReference(getAlternateCatalog(), getAlternateSchema()));

        } else if (getDatabase().supports(Schema.class, JUnitScope.getInstance())) {
            return Arrays.asList(new SchemaReference(getPrimarySchema()), new SchemaReference(getAlternateSchema()));
        } else {
            return Arrays.asList();
        }
    }
}

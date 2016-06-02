package liquibase.database;

import liquibase.AbstractExtensibleObject;
import liquibase.ObjectMetaData;
import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.Snapshot;
import liquibase.util.StringUtil;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link DatabaseConnection} implementation for when there is no direct connection to a database.
 * Connection URL is the format "offline:shortname?param1=x&param2=y" where shortname corresponds to a {@link Database} implementation's shortName.
 * <br><br>
 * Besides the properties defined on this object, you can use the following parameters:
 * <ul>
 * <li>productName</li>
 * <li>version</li>
 * </ul>
 */
@SuppressWarnings("UnusedParameters")
public class OfflineConnection extends AbstractExtensibleObject implements DatabaseConnection {

    public enum OfflineConnectionParameters {
        snapshot;
    }

    //kept private so it is not as changable once the "connection" has been made
    private String url;

    public String databaseShortName;
    public String connectionUserName;
    public String catalog;
    public String schema;

    public String databaseProductName;
    public String databaseProductVersion;

    /**
     * Version defaults to a high number to support newest features by default.
     */
    public int databaseMajorVersion = 999;

    /**
     * Version defaults to a high number to support newest features by default.
     */
    public int databaseMinorVersion = 999;

    //kept private in case subclasses need to handle snapshot creation differently
    private Snapshot snapshot;

    public OutputLiquibaseSql outputLiquibaseSql = OutputLiquibaseSql.NONE;
    public String changeLogFile = "databasechangelog.csv";

    public Database.IdentifierCaseHandling quotedIdentifierCaseHandling = Database.IdentifierCaseHandling.CASE_SENSITIVE;
    public Database.IdentifierCaseHandling unquotedIdentifierCaseHandling = Database.IdentifierCaseHandling.UPPERCASE;

    public boolean sendsStringParametersAsUnicode = true;

    @Override
    public int getPriority(ConnectionParameters parameters, Scope scope) {
        if (parameters.url != null && parameters.url.startsWith("offline:")) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public void openConnection(ConnectionParameters connectionParameters, Scope scope) {
        this.url = connectionParameters.url;
        Matcher matcher = Pattern.compile("offline:(\\w+)\\??(.*)").matcher(url);
        if (!matcher.matches()) {
            throw new UnexpectedLiquibaseException("Could not parse offline url " + url);
        }
        this.databaseShortName = matcher.group(1).toLowerCase();
        String paramString = StringUtil.trimToNull(matcher.group(2));
        Map<String, String> params = new HashMap<>();
        if (paramString != null) {
            String[] keyValues = paramString.split("&");
            for (String param : keyValues) {
                String[] split = param.split("=");
                params.put(split[0], split[1]);
            }
        }


        this.databaseProductName = "Offline " + databaseShortName;
        for (Map.Entry<String, String> paramEntry : params.entrySet()) {
            switch (paramEntry.getKey()) {
                case "version":
                    this.databaseProductVersion = paramEntry.getValue();
                    String[] versionParts = this.databaseProductVersion.split("\\.");
                    try {
                        this.databaseMajorVersion = Integer.valueOf(versionParts[0]);
                        if (versionParts.length > 1) {
                            this.databaseMinorVersion = Integer.valueOf(versionParts[1]);
                        }
                    } catch (NumberFormatException e) {
                        LoggerFactory.getLogger(getClass()).warn("Cannot parse database version " + databaseProductVersion);
                    }
                    break;
                case "productName":
                    this.databaseProductName = paramEntry.getValue();
                    break;
                case "catalog":
                    this.catalog = paramEntry.getValue();
                    break;
                case "quotedIdentifierCaseHandling":
                    this.quotedIdentifierCaseHandling = Database.IdentifierCaseHandling.valueOf(paramEntry.getValue());
                    break;
                case "unquotedIdentifierCaseHandling":
                    this.unquotedIdentifierCaseHandling = Database.IdentifierCaseHandling.valueOf(paramEntry.getValue());
                    break;
                case "changeLogFile":
                    this.changeLogFile = paramEntry.getValue();
                    break;
                case "outputLiquibaseSql":
                    this.outputLiquibaseSql = OutputLiquibaseSql.fromString(paramEntry.getValue());
                    break;
                case "snapshot":
//                    String snapshotFile = paramEntry.getValue();
//                try {
//                    SnapshotParser parser = SnapshotParserFactory.getInstance().getParser(snapshotFile, resourceAccessor);
//                    this.snapshot = parser.parse(snapshotFile, resourceAccessor);
//                    this.snapshot.getDatabase().setConnection(this);

//                    for (Catalog catalog : this.snapshot.get(Catalog.class)) {
//                        if (catalog.isDefault) {
//                            this.catalog = catalog.getSimpleName();
//                        }
//                    }
//                } catch (LiquibaseException e) {
//                    throw new UnexpectedLiquibaseException("Cannot parse snapshot " + url, e);
//                }
                    break;
                case "sendsStringParametersAsUnicode":
                    this.sendsStringParametersAsUnicode = Boolean.parseBoolean(paramEntry.getValue());
                    break;
                default:
                    this.set(paramEntry.getKey(), paramEntry.getValue());
            }
        }

        if (connectionParameters.has(OfflineConnectionParameters.snapshot.name())) {
            this.snapshot = connectionParameters.get(OfflineConnectionParameters.snapshot.name(), Snapshot.class);
        }
    }

    /**
     * Returns true if this connection's shortName equals the database's shortName
     */
    public boolean supports(Database database, Scope scope) {
        return database.getShortName().equalsIgnoreCase(databaseShortName);
    }

    /**
     * Always returns true.
     */
    @Override
    public boolean isReadOnly() throws DatabaseException {
        return true;
    }

    /**
     * Copies all properties on this object onto the passed database, as long as the same property exists on the database class.
     */
    @Override
    public void configureDatabase(Database database, Scope scope) {
        ObjectMetaData objectMetaData = this.getObjectMetaData();
        for (String paramName : this.getAttributes()) {
            if (objectMetaData.getAttribute(paramName) != null) {
                try {
                    database.set(paramName, this.get(paramName, Object.class));
                } catch (Throwable e) {
                    LoggerFactory.getLogger(getClass()).warn("Error setting database parameter " + paramName + ": " + e.getMessage(), e);
                }
            }
        }
//        ChangeLogHistoryServiceFactory.getInstance().register(getChangeLogHistoryService(database));
    }

//    protected ChangeLogHistoryService getChangeLogHistoryService(Database database) {
//        return new OfflineChangeLogHistoryService(database, new File(changeLogFile),
//            outputLiquibaseSql != OutputLiquibaseSql.NONE, // Output DML
//            outputLiquibaseSql == OutputLiquibaseSql.ALL   // Output DDL
//        );
//    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    /**
     * No-op since there is nothing to close.
     */
    @Override
    public void close() throws DatabaseException {
        //nothing
    }

    /**
     * No-op since there is nothing to commit.
     */
    @Override
    public void commit() throws DatabaseException {
        //nothing
    }

    /**
     * Always returns false
     */
    @Override
    public boolean getAutoCommit() throws DatabaseException {
        return false;
    }

    @Override
    public String getCatalog() throws DatabaseException {
        return catalog;
    }

    @Override
    public void setCatalog(String catalogName) throws DatabaseException {
        this.catalog = catalogName;
    }

    @Override
    public String getSchema() throws DatabaseException {
        return schema;
    }

    @Override
    public void setSchema(String schema) throws DatabaseException {
        this.schema = schema;
    }

    /**
     * No-op since there are no transactions.
     */
    @Override
    public void rollback() throws DatabaseException {

    }

    /**
     * No-op since there are no transactions.
     */
    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {

    }

    @Override
    public String getDatabaseProductName() {
        return databaseProductName;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return databaseProductVersion;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return databaseMajorVersion;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        return databaseMinorVersion;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getConnectionUserName() {
        return connectionUserName;
    }

    /**
     * Always returns false
     */
    @Override
    public boolean isClosed() throws DatabaseException {
        return false;
    }

    /**
     * Enumeration of options for how Liquibase-related should be handled.
     */
    public enum OutputLiquibaseSql {
        /**
         * Don't output anything
         */
        NONE,
        /**
         * Output only INSERT/UPDATE/DELETE
         */
        DATA_ONLY,
        /**
         * Output CREATE TABLE as well
         */
        ALL;

        /**
         * For backward compatibility true is translated in ALL and false in NONE
         */
        public static OutputLiquibaseSql fromString(String s) {
            if (s == null) {
                return null;
            }

            switch (s.toUpperCase()) {
                case "TRUE":
                    return ALL;
                case "FALSE":
                    return NONE;
                default:
                    return valueOf(s);
            }
        }
    }
}

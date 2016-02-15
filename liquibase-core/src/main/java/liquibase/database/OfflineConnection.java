package liquibase.database;

import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.Snapshot;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtils;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfflineConnection implements DatabaseConnection {
    private final String url;
    private final String databaseShortName;
    private final Map<String, String> params = new HashMap<String, String>();
    private Snapshot snapshot = null;
    private OutputLiquibaseSql outputLiquibaseSql = OutputLiquibaseSql.NONE;
    private String changeLogFile = "databasechangelog.csv";

    private Database.IdentifierCaseHandling quotedIdentifierCaseHandling = Database.IdentifierCaseHandling.CASE_SENSITIVE;
    private Database.IdentifierCaseHandling unquotedIdentifierCaseHandling = Database.IdentifierCaseHandling.UPPERCASE;

    private String productName;
    private String productVersion;
    private int databaseMajorVersion = 999;
    private int databaseMinorVersion = 999;
    private String catalog;
    private boolean sendsStringParametersAsUnicode = true;

    private final Map<String, String> databaseParams = new HashMap<String, String>();
    private String connectionUserName;

    public OfflineConnection(String url, Snapshot snapshot, ResourceAccessor resourceAccessor) {
        this(url, resourceAccessor);
        this.snapshot = snapshot;
    }

    public OfflineConnection(String url, ResourceAccessor resourceAccessor) {
        this.url = url;
        Matcher matcher = Pattern.compile("offline:(\\w+)\\??(.*)").matcher(url);
        if (!matcher.matches()) {
            throw new UnexpectedLiquibaseException("Could not parse offline url "+url);
        }
        this.databaseShortName = matcher.group(1).toLowerCase();
        String params = StringUtils.trimToNull(matcher.group(2));
        if (params != null) {
            String[] keyValues = params.split("&");
            for (String param : keyValues) {
                String[] split = param.split("=");
                this.params.put(split[0], split[1]);
            }
        }


        this.productName = "Offline "+databaseShortName;
        for (Map.Entry<String, String> paramEntry : this.params.entrySet()) {

            if (paramEntry.getKey().equals("version")) {
                this.productVersion = paramEntry.getValue();
                String[] versionParts = productVersion.split("\\.");
                try {
                    this.databaseMajorVersion = Integer.valueOf(versionParts[0]);
                    if (versionParts.length > 1) {
                        this.databaseMinorVersion = Integer.valueOf(versionParts[1]);
                    }
                } catch (NumberFormatException e) {
                    LoggerFactory.getLogger(getClass()).warn("Cannot parse database version " + productVersion);
                }
            } else if (paramEntry.getKey().equals("productName")) {
                this.productName = paramEntry.getValue();
            } else if (paramEntry.getKey().equals("catalog")) {
                this.catalog = this.params.get("catalog");
            } else if (paramEntry.getKey().equals("quotedIdentifierCaseHandling")) {
                 this.quotedIdentifierCaseHandling = Database.IdentifierCaseHandling.valueOf(paramEntry.getValue());
            } else if (paramEntry.getKey().equals("unquotedIdentifierCaseHandling")) {
                this.unquotedIdentifierCaseHandling = Database.IdentifierCaseHandling.valueOf(paramEntry.getValue());
            } else if (paramEntry.getKey().equals("changeLogFile")) {
                this.changeLogFile = paramEntry.getValue();
            } else if (paramEntry.getKey().equals("outputLiquibaseSql")) {
                this.outputLiquibaseSql = OutputLiquibaseSql.fromString(paramEntry.getValue());
            } else if (paramEntry.getKey().equals("snapshot")) {
                String snapshotFile = paramEntry.getValue();
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
            } else if (paramEntry.getKey().equals("sendsStringParametersAsUnicode")) {
                this.sendsStringParametersAsUnicode = Boolean.parseBoolean(paramEntry.getValue());
            } else {
                this.databaseParams.put(paramEntry.getKey(), paramEntry.getValue());
            }
        }
    }

    public boolean isCorrectDatabaseImplementation(Database database) {
        return database.getShortName().equalsIgnoreCase(databaseShortName);
    }

    @Override
    public void attached(Database database) {
        for (Map.Entry<String, String> param : this.databaseParams.entrySet()) {
            try {
                ObjectUtil.setProperty(database, param.getKey(), param.getValue());
            } catch (Throwable e) {
                LoggerFactory.getLogger(getClass()).warn("Error setting database parameter " + param.getKey() + ": " + e.getMessage(), e);
            }
        }
        if (database instanceof AbstractJdbcDatabase) {
            ((AbstractJdbcDatabase) database).quotedIdentifierCaseHandling = this.quotedIdentifierCaseHandling;
            ((AbstractJdbcDatabase) database).unquotedIdentifierCaseHandling = this.unquotedIdentifierCaseHandling;
        }

//        ChangeLogHistoryServiceFactory.getInstance().register(createChangeLogHistoryService(database));
    }

//    protected ChangeLogHistoryService createChangeLogHistoryService(Database database) {
//        return new OfflineChangeLogHistoryService(database, new File(changeLogFile),
//            outputLiquibaseSql != OutputLiquibaseSql.NONE, // Output DML
//            outputLiquibaseSql == OutputLiquibaseSql.ALL   // Output DDL
//        );
//    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public void close() throws DatabaseException {
        //nothing
    }

    @Override
    public void commit() throws DatabaseException {
        //nothing
    }

    @Override
    public boolean getAutoCommit() throws DatabaseException {
        return false;
    }

    @Override
    public String getCatalog() throws DatabaseException {
        return catalog;
    }

    @Override
    public String nativeSQL(String sql) throws DatabaseException {
        return sql;
    }

    @Override
    public void rollback() throws DatabaseException {

    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {

    }

    @Override
    public String getDatabaseProductName() throws DatabaseException {
        return productName;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return productVersion;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return databaseMajorVersion;
    }

    public void setDatabaseMajorVersion(int databaseMajorVersion) {
        this.databaseMajorVersion = databaseMajorVersion;
    }

    public void setDatabaseMinorVersion(int databaseMinorVersion) {
        this.databaseMinorVersion = databaseMinorVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public void setConnectionUserName(String connectionUserName) {
        this.connectionUserName = connectionUserName;
    }

    @Override
    public boolean isClosed() throws DatabaseException {
        return false;
    }

    /**
     * Output Liquibase SQL
     */
    private static enum OutputLiquibaseSql {
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

        public static OutputLiquibaseSql fromString(String s) {
            if (s == null) {
                return null;
            }
            s = s.toUpperCase();
            // For backward compatibility true is translated in ALL and false in NONE
            if (s.equals("TRUE")) {
                return ALL;
            } else if (s.equals("FALSE")) {
                return NONE;
            } else {
                return valueOf(s);
            }
        }
    }

    public boolean getSendsStringParametersAsUnicode() {
        return sendsStringParametersAsUnicode;
    }

    public void setSendsStringParametersAsUnicode(boolean sendsStringParametersAsUnicode) {
        this.sendsStringParametersAsUnicode = sendsStringParametersAsUnicode;
    }

    public Database.IdentifierCaseHandling getQuotedIdentifierCaseHandling() {
        return quotedIdentifierCaseHandling;
    }

    public void setQuotedIdentifierCaseHandling(Database.IdentifierCaseHandling quotedIdentifierCaseHandling) {
        this.quotedIdentifierCaseHandling = quotedIdentifierCaseHandling;
    }

    public Database.IdentifierCaseHandling getUnquotedIdentifierCaseHandling() {
        return unquotedIdentifierCaseHandling;
    }

    public void setUnquotedIdentifierCaseHandling(Database.IdentifierCaseHandling unquotedIdentifierCaseHandling) {
        this.unquotedIdentifierCaseHandling = unquotedIdentifierCaseHandling;
    }
}

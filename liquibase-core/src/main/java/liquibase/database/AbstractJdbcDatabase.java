package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.ISODateFormat;
import liquibase.util.StringUtils;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * AbstractJdbcDatabase is extended by all supported databases as a facade to the underlying database.
 * The physical connection can be retrieved from the AbstractJdbcDatabase implementation, as well as any
 * database-specific characteristics such as the datatype for "boolean" fields.
 */
public abstract class AbstractJdbcDatabase implements Database {

    private static final Pattern startsWithNumberPattern = Pattern.compile("^[0-9].*");

    private DatabaseConnection connection;

    protected String quotingStartCharacter = "\"";
    protected String quotingEndCharacter = "\"";
    protected String quotingEndReplacement = "\"\"";

    private Boolean previousAutoCommit;

    protected BigInteger defaultAutoIncrementStartWith = BigInteger.ONE;
    protected BigInteger defaultAutoIncrementBy = BigInteger.ONE;
    // most databases either lowercase or uppercase unuqoted objects such as table and column names.
    protected Boolean unquotedObjectsAreUppercased = null;
    // whether object names should be quoted

    private final Set<String> reservedWords = new HashSet<String>();

    protected Boolean caseSensitive;

    private Boolean storesLowerCaseIdentifiers;
    private Boolean storesLowerCaseQuotedIdentifiers;
    private Boolean storesMixedCaseIdentifiers;
    private Boolean storesMixedCaseQuotedIdentifiers;
    private Boolean storesUpperCaseIdentifiers;
    private Boolean storesUpperCaseQuotedIdentifiers;
    protected String currentDateTimeFunction;

    @Override
    public int getPriority(Scope scope) {
        return PRIORITY_DEFAULT;
    }

    public String getName() {
        return toString();
    }

    @Override
    public boolean requiresPassword() {
        return true;
    }

    @Override
    public boolean requiresUsername() {
        return true;
    }

    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    // ------- DATABASE INFORMATION METHODS ---- //

    @Override
    public DatabaseConnection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(final DatabaseConnection conn) {
        if (this.connection != null && this.connection.equals(conn)) {
            return;
        }
        LoggerFactory.getLogger(getClass()).debug("Connected to " + conn.toString());
        this.connection = conn;
        try {
            boolean autoCommit = conn.getAutoCommit();
            if (autoCommit == getAutoCommitMode()) {
                // Don't adjust the auto-commit mode if it's already what the database wants it to be.
                LoggerFactory.getLogger(getClass()).debug("Not adjusting the auto commit mode; it is already " + autoCommit);
            } else {
                // Store the previous auto-commit mode, because the connection needs to be restored to it when this
                // AbstractDatabase type is closed. This is important for systems which use connection pools.
                previousAutoCommit = autoCommit;

                LoggerFactory.getLogger(getClass()).debug("Setting auto commit to " + getAutoCommitMode() + " from " + autoCommit);
                connection.setAutoCommit(getAutoCommitMode());

            }
        } catch (DatabaseException e) {
            LoggerFactory.getLogger(getClass()).warn("Cannot set auto commit to " + getAutoCommitMode() + " on connection");
        }

        this.connection.attached(this);
    }

    /**
     * Auto-commit mode to run in
     */
    @Override
    public boolean getAutoCommitMode() {
        return !supportsDDLInTransaction();
    }

    /**
     * By default databases should support DDL within a transaction.
     */
    @Override
    public boolean supportsDDLInTransaction() {
        return true;
    }

    /**
     * Returns the name of the database product according to the underlying database.
     */
    @Override
    public String getDatabaseProductName() {
        if (connection == null) {
            return getDefaultDatabaseProductName();
        }

        try {
            return connection.getDatabaseProductName();
        } catch (DatabaseException e) {
            throw new RuntimeException("Cannot get database name");
        }
    }

    protected abstract String getDefaultDatabaseProductName();


    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        if (connection == null) {
            return null;
        }

        try {
            return connection.getDatabaseProductVersion();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (connection == null) {
            return 999;
        }
        try {
            return connection.getDatabaseMajorVersion();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (connection == null) {
            return -1;
        }
        try {
            return connection.getDatabaseMinorVersion();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Returns system (undroppable) views.
     */
    protected Set<String> getSystemTables() {
        return new HashSet<String>();
    }

    protected Set<String> getSystemViews() {
        return new HashSet<String>();
    }


    @Override
    public boolean supports(Class<? extends DatabaseObject> type) {
        if (type.isAssignableFrom(Catalog.class)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supportsAutoIncrement() {
        return true;
    }

    // ------- DATABASE-SPECIFIC SQL METHODS ---- //

    @Override
    public void setCurrentDateTimeFunction(final String function) {
        if (function != null) {
            this.currentDateTimeFunction = function;
        }
    }

    /**
     * Return a date literal with the same value as a string formatted using ISO 8601.
     * <p/>
     * Note: many databases accept date literals in ISO8601 format with the 'T' replaced with
     * a space. Only databases which do not accept these strings should need to override this
     * method.
     * <p/>
     * Implementation restriction:
     * Currently, only the following subsets of ISO8601 are supported:
     * yyyy-MM-dd
     * hh:mm:ss
     * yyyy-MM-ddThh:mm:ss
     */
    public String getDateLiteral(final String isoDate) {
        if (isDateOnly(isoDate) || isTimeOnly(isoDate)) {
            return "'" + isoDate + "'";
        } else if (isDateTime(isoDate)) {
//            StringBuffer val = new StringBuffer();
//            val.append("'");
//            val.append(isoDate.substring(0, 10));
//            val.append(" ");
////noinspection MagicNumber
//            val.append(isoDate.substring(11));
//            val.append("'");
//            return val.toString();
            return "'" + isoDate.replace('T', ' ') + "'";
        } else {
            return "BAD_DATE_FORMAT:" + isoDate;
        }
    }


    @Override
    public String getDateTimeLiteral(final java.sql.Timestamp date) {
        return getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getDateLiteral(final java.sql.Date date) {
        return getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getTimeLiteral(final java.sql.Time date) {
        return getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getDateLiteral(final Date date) {
        if (date instanceof java.sql.Date) {
            return getDateLiteral(((java.sql.Date) date));
        } else if (date instanceof java.sql.Time) {
            return getTimeLiteral(((java.sql.Time) date));
        } else if (date instanceof java.sql.Timestamp) {
            return getDateTimeLiteral(((java.sql.Timestamp) date));
        } else {
            throw new RuntimeException("Unexpected type: " + date.getClass().getName());
        }
    }

    protected boolean isDateOnly(final String isoDate) {
        return isoDate.length() == "yyyy-MM-dd".length();
    }

    protected boolean isDateTime(final String isoDate) {
        return isoDate.length() >= "yyyy-MM-ddThh:mm:ss".length();
    }

    protected boolean isTimeOnly(final String isoDate) {
        return isoDate.length() == "hh:mm:ss".length();
    }


    /**
     * Returns database-specific line comment string.
     */
    @Override
    public String getLineComment() {
        return "--";
    }

    protected String getAutoIncrementClause() {
        return "GENERATED BY DEFAULT AS IDENTITY";
    }

    protected boolean generateAutoIncrementStartWith(final BigInteger startWith) {
        return startWith != null
                && !startWith.equals(defaultAutoIncrementStartWith);
    }

    protected boolean generateAutoIncrementBy(final BigInteger incrementBy) {
        return incrementBy != null
                && !incrementBy.equals(defaultAutoIncrementBy);
    }

    protected String getAutoIncrementOpening() {
        return " (";
    }

    protected String getAutoIncrementClosing() {
        return ")";
    }

    protected String getAutoIncrementStartWithClause() {
        return "START WITH %d";
    }

    protected String getAutoIncrementByClause() {
        return "INCREMENT BY %d";
    }

    public boolean storesLowerCaseIdentifiers() {
        if (storesLowerCaseIdentifiers == null) {
            final boolean DEFAULT_VALUE = false;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesLowerCaseIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
                    }
                } else {
                    storesLowerCaseIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesLowerCaseIdentifiers", e);
                storesLowerCaseIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesLowerCaseIdentifiers;
    }

    public boolean storesLowerCaseQuotedIdentifiers() {
        if (storesLowerCaseQuotedIdentifiers == null) {
            final boolean DEFAULT_VALUE = false;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesLowerCaseQuotedIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesLowerCaseQuotedIdentifiers = metaData.storesLowerCaseQuotedIdentifiers();
                    }
                } else {
                    storesLowerCaseQuotedIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesLowerCaseQuotedIdentifiers", e);
                storesLowerCaseQuotedIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesLowerCaseQuotedIdentifiers;
    }

    public boolean storesMixedCaseIdentifiers() {
        if (storesMixedCaseIdentifiers == null) {
            final boolean DEFAULT_VALUE = false;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesMixedCaseIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesMixedCaseIdentifiers = metaData.storesMixedCaseIdentifiers();
                    }
                } else {
                    storesMixedCaseIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesMixedCaseIdentifiers", e);
                storesMixedCaseIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesMixedCaseIdentifiers;
    }

    public boolean storesMixedCaseQuotedIdentifiers() {
        if (storesMixedCaseQuotedIdentifiers == null) {
            final boolean DEFAULT_VALUE = false;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesMixedCaseQuotedIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesMixedCaseQuotedIdentifiers = metaData.storesMixedCaseQuotedIdentifiers();
                    }
                } else {
                    storesMixedCaseQuotedIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesMixedCaseQuotedIdentifiers", e);
                storesMixedCaseQuotedIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesMixedCaseQuotedIdentifiers;
    }

    public boolean storesUpperCaseIdentifiers() {
        if (storesUpperCaseIdentifiers == null) {
            final boolean DEFAULT_VALUE = true;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesUpperCaseIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesUpperCaseIdentifiers = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData().storesUpperCaseIdentifiers();
                    }
                } else {
                    storesUpperCaseIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesUpperCaseIdentifiers", e);
                storesUpperCaseIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesUpperCaseIdentifiers;
    }

    public boolean storesUpperCaseQuotedIdentifiers() {
        if (storesUpperCaseQuotedIdentifiers == null) {
            final boolean DEFAULT_VALUE = true;
            try {
                if (getConnection() != null && getConnection() instanceof JdbcConnection && ((JdbcConnection) getConnection()).getUnderlyingConnection() != null) {
                    DatabaseMetaData metaData = ((JdbcConnection) getConnection()).getUnderlyingConnection().getMetaData();
                    if (metaData == null) {
                        storesUpperCaseQuotedIdentifiers = DEFAULT_VALUE;
                    } else {
                        storesUpperCaseQuotedIdentifiers = metaData.storesUpperCaseQuotedIdentifiers();
                    }
                } else {
                    storesUpperCaseQuotedIdentifiers = DEFAULT_VALUE;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot look up storesUpperCaseQuotedIdentifiers", e);
                storesUpperCaseQuotedIdentifiers = DEFAULT_VALUE;
            }
        }
        return storesUpperCaseQuotedIdentifiers;
    }

    public boolean canStoreObjectName(String name, Class<? extends DatabaseObject> type) {
        return canStoreObjectName(name, false, type) || canStoreObjectName(name, true, type);
    }

    public boolean canStoreObjectName(String name, boolean quoted, Class<? extends DatabaseObject> type) {
        if (name.matches("[a-z_0-9]+")) {
            if (quoted) {
                return storesLowerCaseQuotedIdentifiers();
            } else {
                return storesLowerCaseIdentifiers();
            }
        } else if (name.matches("[A-Z_0-9]+")) {
            if (quoted) {
                return storesUpperCaseQuotedIdentifiers();
            } else {
                return storesUpperCaseIdentifiers();
            }
        } else if (name.matches("[a-zA-Z_0-9]+")) {
            if (quoted) {
                return storesMixedCaseQuotedIdentifiers();
            } else {
                return storesMixedCaseIdentifiers();
            }
        } else {
            return quoted;
        }
    }

    @Override
    public boolean isCaseSensitive(Class<? extends DatabaseObject> type) {
        final boolean DEFAULT_CASE_SENSITIVE = true;

        if (caseSensitive == null) {
            if (connection != null && connection instanceof JdbcConnection) {
                try {
                    Connection underlyingConnection = ((JdbcConnection) connection).getUnderlyingConnection();
                    if (underlyingConnection == null) {
                        caseSensitive = DEFAULT_CASE_SENSITIVE;
                    } else {
                        DatabaseMetaData metaData = underlyingConnection.getMetaData();
                        if (metaData == null) {
                            caseSensitive = DEFAULT_CASE_SENSITIVE;
                        } else {
                            caseSensitive = metaData.supportsMixedCaseIdentifiers();
                        }
                    }
                } catch (SQLException e) {
                    LoggerFactory.getLogger(getClass()).warn("Cannot determine case sensitivity from JDBC driver", e);
                }
            }
        }

    	if (caseSensitive == null) {
            return DEFAULT_CASE_SENSITIVE;
    	} else {
    		return caseSensitive.booleanValue();
    	}
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean isReservedWord(final String string) {
        return reservedWords.contains(string.toUpperCase());
    }

    /*
    * Check if given string starts with numeric values that may cause problems and should be escaped.
    */
    protected boolean startsWithNumeric(final String objectName) {
        return startsWithNumberPattern.matcher(objectName).matches();
    }

// ------- DATABASE OBJECT DROPPING METHODS ---- //

    @Override
    public boolean requiresDefiningColumnsAsNull() {
        return false;
    }

    @Override
    public boolean isSystemObject(final ObjectReference example) {
        if (example == null) {
            return false;
        }
        if (example.container != null && example.container.name != null && example.container.name.equalsIgnoreCase("information_schema")) {
            return true;
        }
        if (example.instanceOf(Table.class) && getSystemTables().contains(example.name)) {
            return true;
        }

        if (example.instanceOf(View.class) && getSystemViews().contains(example.name)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isLiquibaseObject(final ObjectReference object) {
//        if (Table.class.isAssignableFrom(object.type)) {
//            Schema liquibaseSchema = new Schema(new ObjectReference(getLiquibaseCatalogName()), getLiquibaseSchemaName());
////            if (DatabaseObjectComparatorFactory.getInstance().isSameObject(object, new Table(new ObjectName(getDatabaseChangeLogTableName())).setSchema(liquibaseSchema), this)) {
////                return true;
////            }
////            if (DatabaseObjectComparatorFactory.getInstance().isSameObject(object, new Table(new ObjectName(getDatabaseChangeLogLockTableName())).setSchema(liquibaseSchema), this)) {
////                return true;
////            }
//            return false;
//        } else if (Column.class.isAssignableFrom(object.type)) {
//            return isLiquibaseObject(object.container);
//        } else if (Index.class.isAssignableFrom(object.type)) {
//            return isLiquibaseObject(((Index.IndexReference) object).table);
//        } else if (PrimaryKey.class.isAssignableFrom(object.type)) {
////            return isLiquibaseObject(((PrimaryKey) object).getTable());
//        }
        return false;
    }

    @Override
    public String toString() {
        if (getConnection() == null) {
            return getShortName() + " Database";
        }

        return getClass().getName()+" " +getConnection().toString();
    }


    @Override
    public String escapeObjectName(String objectName, final Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        } else {
            return quoteObject(objectName.trim(), objectType);
        }
    }

    @Override
    public String escapeObjectName(ObjectReference objectReference) {
        Class<? extends DatabaseObject> objectType = objectReference.type;
        if (objectType == null) {
            objectType = DatabaseObject.class;
        }

        if (objectType.isAssignableFrom(Column.class) || objectType.isAssignableFrom(PrimaryKey.class)  || objectType.isAssignableFrom(UniqueConstraint.class)) {
            return escapeObjectName(objectReference.name, objectType);
        }

        int length = 1;
        if (supports(Schema.class)) {
            length++;
        }
        if (supports(Catalog.class)) {
            length++;
        }
        return StringUtils.join(objectReference.truncate(length).asList(), ".", new StringUtils.ObjectStringNameFormatter(objectType, this));
    }

    protected boolean mustQuoteObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        return objectName.contains("-") || startsWithNumeric(objectName) || isReservedWord(objectName) || objectName.matches(".*\\W.*") || (!canStoreObjectName(objectName, false, objectType) && canStoreObjectName(objectName, true, objectType));
    }

    public String quoteObject(final String objectName, final Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }

        return quotingStartCharacter
                + objectName.replace(quotingEndCharacter, quotingEndReplacement)
                + quotingEndCharacter;
    }

    public boolean jdbcCallsCatalogsSchemas() {
        return false;
    }

    @Override
    public String generatePrimaryKeyName(final String tableName) {
        return "PK_" + tableName.toUpperCase();
    }

    @Override
    public String escapeString(final String string) {
        if (string == null) {
            return null;
        }
        return string.replaceAll("'", "''");
    }

    @Override
    public void commit() throws DatabaseException {
        try {
            getConnection().commit();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void rollback() throws DatabaseException {
        try {
            getConnection().rollback();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractJdbcDatabase that = (AbstractJdbcDatabase) o;

        if (connection == null) {
            if (that.connection == null) {
                return this == that;
            } else {
                return false;
            }
        } else {
            return connection.equals(that.connection);
        }
    }

    @Override
    public int hashCode() {
        return (connection != null ? connection.hashCode() : super.hashCode());
    }

    @Override
    public void close() throws DatabaseException {
        DatabaseConnection connection = getConnection();
        if (connection != null) {
            if (previousAutoCommit != null) {
                try {
                    connection.setAutoCommit(previousAutoCommit);
                } catch (DatabaseException e) {
                    LoggerFactory.getLogger(getClass()).warn("Failed to restore the auto commit to " + previousAutoCommit);

                    throw e;
                }
            }
            connection.close();
        }
//        ExecutorService.getInstance().clearExecutor(this);
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return true;
    }

    @Override
    public boolean isAutoCommit() throws DatabaseException {
        try {
            return getConnection().getAutoCommit();
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setAutoCommit(final boolean b) throws DatabaseException {
        try {
            getConnection().setAutoCommit(b);
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean supportsForeignKeyDisable() {
        return false;
    }

    @Override
    public boolean disableForeignKeyChecks() throws DatabaseException {
        throw new DatabaseException("ForeignKeyChecks Management not supported");
    }

    @Override
    public void enableForeignKeyChecks() throws DatabaseException {
        throw new DatabaseException("ForeignKeyChecks Management not supported");
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return false;
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return currentDateTimeFunction;
    }

    @Override
    public boolean supportsPrimaryKeyNames() {
        return true;
    }

    @Override
    public String escapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }


    @Override
    public boolean supportsClustered(Class<? extends DatabaseObject> objectType) {
        return false;
    }

    public boolean looksLikeFunctionCall(String value) {
        return value.startsWith("\"SYSIBM\"") || value.startsWith("to_date(") || value.equalsIgnoreCase(getCurrentDateTimeFunction());
    }

    public boolean metaDataCallsSchemasCatalogs() {
        return false;
    }

    public String escapeStringForLike(String string) {
        if (string == null) {
            return null;
        }
        return (string.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_"));
    }
}

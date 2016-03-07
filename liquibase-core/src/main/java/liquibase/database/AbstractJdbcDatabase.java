package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.item.*;
import liquibase.item.core.*;
import liquibase.plugin.AbstractPlugin;
import liquibase.util.SmartMap;
import liquibase.util.StringUtil;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Convenience base class for Database implementations that describe a standard SQL/JDBC based database.
 * This base class should follow SQL standard as closely as possible.
 * <br><br>
 * SQL reference: http://savage.net.au/SQL/sql-2003-2.bnf.html
 * Database feature comparison: http://www.sql-workbench.net/dbms_comparison.html
 */
public abstract class AbstractJdbcDatabase extends AbstractPlugin implements Database {

    /**
     * Kept private to force use of {@link #setConnection(DatabaseConnection, Scope)}
     */
    private DatabaseConnection connection;

    public String identifierStartQuote = "\"";
    public String identifierEndQuote = "\"";
    public IdentifierCaseHandling quotedIdentifierCaseHandling;
    public IdentifierCaseHandling unquotedIdentifierCaseHandling;

    public String currentDateTimeFunction = "CURRENT_TIMESTAMP(2)";

    //Automatically populated with values in block at end of this file
    public Set<String> reservedWords = new HashSet<>();

    /**
     * Default implementation returns PRIORITY_DEFAULT
     */
    @Override
    public int getPriority(Scope scope) {
        return PRIORITY_DEFAULT;
    }

    @Override
    public DatabaseConnection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(final DatabaseConnection conn, Scope scope) {
        if (this.connection != null && this.connection.equals(conn)) {
            return;
        }
        this.connection = conn;
    }

    /**
     * Default implementation returns the {@link Feature#getSupportedByDefault()} value for the feature.
     */
    @Override
    public boolean supports(Feature feature, Scope scope) {
        return feature.getSupportedByDefault();
    }

    /**
     * If featureKey matches a {@link liquibase.database.Database.Feature} then call {@link #supports(Feature, Scope)}. Otherwise, return false.
     */
    @Override
    public boolean supports(String featureKey, Scope scope) {
        try {
            return supports(Feature.valueOf(featureKey), scope);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Default implementation returns true for all object types except {@link Catalog}
     */
    @Override
    public boolean supports(Class<? extends Item> type, Scope scope) {
        if (type.isAssignableFrom(Catalog.class)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a SQL-standard datetime literal
     */
    @Override
    public String getDateTimeString(java.util.Date date, Scope scope) {
        return "TIMESTAMP '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date) + "'";
    }

    /**
     * Returns a SQL-standard date literal
     */
    @Override
    public String getDateString(java.util.Date date, Scope scope) {
        return "DATE '" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'";
    }

    /**
     * Returns a SQL-standard time literal
     */
    @Override
    public String getTimeString(java.util.Date date, Scope scope) {
        return "TIME '" + new SimpleDateFormat("HH:mm:ss.SSS").format(date) + "'";
    }

    /**
     * Default implementation returns "-- "
     */
    @Override
    public String getLineComment(Scope scope) {
        return "-- ";
    }

    /**
     * Always returns true if willQuote is true. Otherwise, require the name to start with a non-number and not be a reserved word.
     */
    @Override
    public boolean isValidObjectName(String objectName, boolean willQuote, Class<? extends Item> type, Scope scope) {
        if (willQuote) {
            return true;
        } else {
            return !(!objectName.matches("[a-zA-Z]\\w+") || isReservedWord(objectName, scope));
        }
    }

    /**
     * Uses the {@link #quotedIdentifierCaseHandling} and {@link #unquotedIdentifierCaseHandling} if set, otherwise checks the connection's metadata.
     * If connection is not set or is not a {@link JdbcConnection}, returns {@link liquibase.database.Database.IdentifierCaseHandling#CASE_SENSITIVE} if quoted and {@link liquibase.database.Database.IdentifierCaseHandling#UPPERCASE} for unquoted names
     */
    @Override
    public IdentifierCaseHandling getIdentifierCaseHandling(Class<? extends Item> type, boolean quoted, Scope scope) {
        IdentifierCaseHandling DEFAULT_QUOTED_HANDLING = IdentifierCaseHandling.CASE_SENSITIVE;
        IdentifierCaseHandling DEFAULT_UNQUOTED_HANDLING = IdentifierCaseHandling.UPPERCASE;

        if (quoted && quotedIdentifierCaseHandling != null) {
            return quotedIdentifierCaseHandling;
        } else if (!quoted && unquotedIdentifierCaseHandling != null) {
            return unquotedIdentifierCaseHandling;
        }

        if (connection == null) {
            if (quoted) {
                return DEFAULT_QUOTED_HANDLING;
            } else {
                return DEFAULT_UNQUOTED_HANDLING;
            }
        }

        if (connection instanceof JdbcConnection) {
            try {
                Connection underlyingConnection = ((JdbcConnection) connection).getUnderlyingConnection();
                if (underlyingConnection == null) {
                    if (quoted) {
                        return DEFAULT_QUOTED_HANDLING;
                    } else {
                        return DEFAULT_UNQUOTED_HANDLING;
                    }
                }

                DatabaseMetaData metaData = underlyingConnection.getMetaData();
                if (metaData == null) {
                    if (quoted) {
                        return DEFAULT_QUOTED_HANDLING;
                    } else {
                        return DEFAULT_UNQUOTED_HANDLING;
                    }
                }

                if (quoted) {
                    if (metaData.supportsMixedCaseQuotedIdentifiers()) {
                        this.quotedIdentifierCaseHandling = IdentifierCaseHandling.CASE_SENSITIVE;
                    } else if (metaData.storesLowerCaseQuotedIdentifiers()) {
                        this.quotedIdentifierCaseHandling = IdentifierCaseHandling.LOWERCASE;
                    } else {
                        this.quotedIdentifierCaseHandling = IdentifierCaseHandling.UPPERCASE;
                    }

                    return this.quotedIdentifierCaseHandling;
                } else {
                    if (metaData.supportsMixedCaseIdentifiers()) {
                        this.unquotedIdentifierCaseHandling = IdentifierCaseHandling.CASE_SENSITIVE;
                    } else if (metaData.storesLowerCaseIdentifiers()) {
                        this.unquotedIdentifierCaseHandling = IdentifierCaseHandling.LOWERCASE;
                    } else {
                        this.unquotedIdentifierCaseHandling = IdentifierCaseHandling.UPPERCASE;
                    }

                    return this.unquotedIdentifierCaseHandling;
                }
            } catch (SQLException e) {
                LoggerFactory.getLogger(getClass()).warn("Cannot determine case sensitivity from JDBC driver", e);
            }
        }

        if (quoted) {
            return DEFAULT_QUOTED_HANDLING;
        } else {
            return DEFAULT_UNQUOTED_HANDLING;
        }
    }

    /**
     * Checks an upper-case version of the word against the values in {@link #reservedWords}
     */
    @Override
    public boolean isReservedWord(final String word, Scope scope) {
        return reservedWords.contains(word.toUpperCase());
    }

    /**
     * Walks up the object's container hierarchy and returns true if any match {@link #isSystemSchema(String, Scope)}.
     * Normally subclasses only need to override {@link #isSystemSchema(String, Scope)}
     */
    @Override
    public boolean isSystemObject(ItemReference object, Scope scope) {
        if (object == null) {
            return false;
        }

        while (object != null) {
            if (object.name != null && isSystemSchema(object.name, scope)) {
                return true;
            }
            object = object.container;
        }
        return false;
    }

    /**
     * Used by {@link #isSystemObject(ItemReference, Scope)}. Default implementation returns true for "information_schema"
     */
    protected boolean isSystemSchema(String schemaName, Scope scope) {
        return schemaName.equalsIgnoreCase("information_schema");
    }

    @Override
    public boolean isLiquibaseObject(final ItemReference object, Scope scope) {
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
            return getClass().getName();
        }

        return getClass().getName() + " " + getConnection().toString();
    }


    /**
     * Converts the given resultSet to a list of {@link SmartMap}, using {@link #getValue(ResultSet, int)} on each cell.
     */
    public List<SmartMap> extract(ResultSet rs, Scope scope) throws DatabaseException {
        try {
            List<SmartMap> rows = new ArrayList<>();
            while (rs.next()) {
                SmartMap row = new SmartMap();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String key = metaData.getColumnLabel(i);
                    Object obj = getValue(rs, i);
                    row.put(key, obj);
                }
                rows.add(row);
            }

            return rows;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Retrieve a JDBC column value from a ResultSet, using the most appropriate
     * value type. The returned value should be a detached value object, not having
     * any ties to the active ResultSet: in particular, it should not be a Blob or
     * Clob object but rather a byte array respectively String representation.
     * <br><br>
     * Uses the {@link ResultSet#getObject(int)} method, but subclasses can work around
     * around database-specific non-standard handling.
     */
    protected Object getValue(ResultSet rs, int index) throws SQLException {
//        try {
        Object obj = rs.getObject(index);
//        } catch (SQLException e) {
//            if (e.getMessage().equals("The conversion from char to SMALLINT is unsupported.")) {
//                issue with sqlserver jdbc 3.0 http://social.msdn.microsoft.com/Forums/sqlserver/en-US/2c908b45-6f75-484a-a891-5e8206f8844f/conversion-error-in-the-jdbc-30-driver-when-accessing-metadata
//                obj = rs.getString(index);
//            } else {
//                throw e;
//            }
//        }
        if (obj instanceof Blob) {
            obj = rs.getBytes(index);
        } else if (obj instanceof Clob) {
            obj = rs.getString(index);
//        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
//            obj = rs.getTimestamp(index);
//        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE")) {
//            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
//            if ("java.sql.Timestamp".equals(metaDataClassName) ||
//                    "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
//                obj = rs.getTimestamp(index);
//            } else {
//                obj = rs.getDate(index);
//            }
//        } else if (obj != null && obj instanceof java.sql.Date) {
//            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
//                obj = rs.getTimestamp(index);
//            }
        }
        return obj;
    }

    /**
     * Surrounds objectName with {@link #identifierStartQuote} and {@link #identifierEndQuote}.
     * If quoting char is ", replace quotes in object name with double quotes. Otherwise, replace identifierStartQuote and identifierEndQuote chars with backslashed versions.
     */
    @Override
    public String quoteObjectName(String objectName, final Class<? extends DatabaseObject> objectType, Scope scope) {
        if (objectName == null) {
            return null;
        }

        if (identifierStartQuote.equals("\"")) {
            objectName = objectName.replace(identifierStartQuote, "\"\"");
        } else {
            objectName = objectName.replace(identifierStartQuote, "\\" + identifierStartQuote);
            objectName = objectName.replace(identifierEndQuote, "\\" + identifierEndQuote);
        }

        return identifierStartQuote
                + objectName
                + identifierEndQuote;
    }

    /**
     * Uses {@link Database#quoteObjectName(String, Class, Scope)} to quote all levels of the {@link ItemReference}.
     * Only quotes levels in the reference up to the length returned by {@link #getMaxObjectPathLength(Class, Scope)}
     */
    @Override
    public String quoteObjectName(DatabaseObjectReference reference, Scope scope) {
        if (reference == null) {
            return null;
        }
        Class<? extends DatabaseObject> objectType = reference.type;
        if (objectType == null) {
            objectType = DatabaseObject.class;
        }

        List<String> parts = reference.asList();
        int maxLength = getMaxObjectPathLength(objectType, scope);
        if (parts.size() > maxLength) {
            parts = new ArrayList<>(parts);
            Collections.reverse(parts);
            parts = parts.subList(0, maxLength);
            Collections.reverse(parts);
        }

        return StringUtil.join(parts, ".", new StringUtil.DatabaseObjectNameFormatter(objectType, scope));
    }


    /**
     * If the objectType is a {@link liquibase.item.AbstractRelationBasedObject.RelationBasedObjectReference} then return 1. Otherwise, checks if the database supports Catalogs and/or Schemas to determine the length.
     * Used by {@link Database#quoteObjectName(DatabaseObjectReference, Scope)}
     */
    protected int getMaxObjectPathLength(Class<? extends Item> objectType, Scope scope) {
        if (AbstractRelationBasedObject.class.isAssignableFrom(objectType)) {
            return 1;
        } else {
            int length = 1;
            if (supports(Schema.class, scope)) {
                length++;
            }
            if (supports(Catalog.class, scope)) {
                length++;
            }
            return length;
        }
    }

    /**
     * Default implementation surrounds with ' chars, and escapes ' as ''.
     */
    @Override
    public String quoteString(final String string, Scope scope) {
        if (string == null) {
            return null;
        }
        return "'" + string.replaceAll("'", "''") + "'";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String getCurrentDateTimeFunction(Scope scope) {
        return currentDateTimeFunction;
    }

    /**
     * @return return true if the "schema" level is returned in catalog columns when calling jdbc metadata.
     */
    public boolean metaDataCallsSchemasCatalogs() {
        return false;
    }

    /**
     * Escape any like-function wildcards in a string.
     * Does not quote the string or do any processing to make it quote-ready. If you that is needed, use {@link #quoteString(String, Scope)}
     */
    public String escapeStringForLike(String string) {
        if (string == null) {
            return null;
        }
        string = string.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");

        return string;
    }

    {
        //list from http://savage.net.au/SQL/sql-2003-2.bnf.html#reserved word
        reservedWords.addAll(Arrays.asList(
                "ADD",
                "ALL",
                "ALLOCATE",
                "ALTER",
                "AND",
                "ANY",
                "ARE",
                "ARRAY",
                "AS",
                "ASENSITIVE",
                "ASYMMETRIC",
                "AT",
                "ATOMIC",
                "AUTHORIZATION",
                "BEGIN",
                "BETWEEN",
                "BIGINT",
                "BINARY",
                "BLOB",
                "BOOLEAN",
                "BOTH",
                "BY",
                "CALL",
                "CALLED",
                "CASCADED",
                "CASE",
                "CAST",
                "CHAR",
                "CHARACTER",
                "CHECK",
                "CLOB",
                "CLOSE",
                "COLLATE",
                "COLUMN",
                "COMMIT",
                "CONNECT",
                "CONSTRAINT",
                "CONTINUE",
                "CORRESPONDING",
                "CREATE",
                "CROSS",
                "CUBE",
                "CURRENT",
                "CURRENT_DATE",
                "CURRENT_DEFAULT_TRANSFORM_GROUP",
                "CURRENT_PATH",
                "CURRENT_ROLE",
                "CURRENT_TIME",
                "CURRENT_TIMESTAMP",
                "CURRENT_TRANSFORM_GROUP_FOR_TYPE",
                "CURRENT_USER",
                "CURSOR",
                "CYCLE",
                "DATE",
                "DAY",
                "DEALLOCATE",
                "DEC",
                "DECIMAL",
                "DECLARE",
                "DEFAULT",
                "DELETE",
                "DEREF",
                "DESCRIBE",
                "DETERMINISTIC",
                "DISCONNECT",
                "DISTINCT",
                "DOUBLE",
                "DROP",
                "DYNAMIC",
                "EACH",
                "ELEMENT",
                "ELSE",
                "END",
                "END-EXEC",
                "ESCAPE",
                "EXCEPT",
                "EXEC",
                "EXECUTE",
                "EXISTS",
                "EXTERNAL",
                "FALSE",
                "FETCH",
                "FILTER",
                "FLOAT",
                "FOR",
                "FOREIGN",
                "FREE",
                "FROM",
                "FULL",
                "FUNCTION",
                "GET",
                "GLOBAL",
                "GRANT",
                "GROUP",
                "GROUPING",
                "HAVING",
                "HOLD",
                "HOUR",
                "IDENTITY",
                "IMMEDIATE",
                "IN",
                "INDICATOR",
                "INNER",
                "INOUT",
                "INPUT",
                "INSENSITIVE",
                "INSERT",
                "INT",
                "INTEGER",
                "INTERSECT",
                "INTERVAL",
                "INTO",
                "IS",
                "ISOLATION",
                "JOIN",
                "LANGUAGE",
                "LARGE",
                "LATERAL",
                "LEADING",
                "LEFT",
                "LIKE",
                "LOCAL",
                "LOCALTIME",
                "LOCALTIMESTAMP",
                "MATCH",
                "MEMBER",
                "MERGE",
                "METHOD",
                "MINUTE",
                "MODIFIES",
                "MODULE",
                "MONTH",
                "MULTISET",
                "NATIONAL",
                "NATURAL",
                "NCHAR",
                "NCLOB",
                "NEW",
                "NO",
                "NONE",
                "NOT",
                "NULL",
                "NUMERIC",
                "OF",
                "OLD",
                "ON",
                "ONLY",
                "OPEN",
                "OR",
                "ORDER",
                "OUT",
                "OUTER",
                "OUTPUT",
                "OVER",
                "OVERLAPS",
                "PARAMETER",
                "PARTITION",
                "PRECISION",
                "PREPARE",
                "PRIMARY",
                "PROCEDURE",
                "RANGE",
                "READS",
                "REAL",
                "RECURSIVE",
                "REF",
                "REFERENCES",
                "REFERENCING",
                "REGR_AVGX",
                "REGR_AVGY",
                "REGR_COUNT",
                "REGR_INTERCEPT",
                "REGR_R2",
                "REGR_SLOPE",
                "REGR_SXX",
                "REGR_SXY",
                "REGR_SYY",
                "RELEASE",
                "RESULT",
                "RETURN",
                "RETURNS",
                "REVOKE",
                "RIGHT",
                "ROLLBACK",
                "ROLLUP",
                "ROW",
                "ROWS",
                "SAVEPOINT",
                "SCROLL",
                "SEARCH",
                "SECOND",
                "SELECT",
                "SENSITIVE",
                "SESSION_USER",
                "SET",
                "SIMILAR",
                "SMALLINT",
                "SOME",
                "SPECIFIC",
                "SPECIFICTYPE",
                "SQL",
                "SQLEXCEPTION",
                "SQLSTATE",
                "SQLWARNING",
                "START",
                "STATIC",
                "SUBMULTISET",
                "SYMMETRIC",
                "SYSTEM",
                "SYSTEM_USER",
                "TABLE",
                "THEN",
                "TIME",
                "TIMESTAMP",
                "TIMEZONE_HOUR",
                "TIMEZONE_MINUTE",
                "TO",
                "TRAILING",
                "TRANSLATION",
                "TREAT",
                "TRIGGER",
                "TRUE",
                "UESCAPE",
                "UNION",
                "UNIQUE",
                "UNKNOWN",
                "UNNEST",
                "UPDATE",
                "UPPER",
                "USER",
                "USING",
                "VALUE",
                "VALUES",
                "VAR_POP",
                "VAR_SAMP",
                "VARCHAR",
                "VARYING",
                "WHEN",
                "WHENEVER",
                "WHERE",
                "WIDTH_BUCKET",
                "WINDOW",
                "WITH",
                "WITHIN",
                "WITHOUT",
                "YEAR"));
    }
}

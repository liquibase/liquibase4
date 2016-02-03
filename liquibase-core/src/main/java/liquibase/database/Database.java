package liquibase.database;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.Service;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Index;

import java.io.DataOutput;
import java.util.Date;

public interface Database extends Service {

    int getPriority(Scope scope);

    /**
     * Is this AbstractDatabase subclass the correct one to use for the given connection.
     */
    boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException;

    /**
     * If this database understands the given url, return the default driver class name.  Otherwise return null.
     */
    String getDefaultDriver(String url);

    DatabaseConnection getConnection();

    void setConnection(DatabaseConnection conn);

    boolean requiresUsername();

    boolean requiresPassword();

    /**
     * Auto-commit mode to run in
     */
    boolean getAutoCommitMode();

    /**
     * Determines if the database supports DDL within a transaction or not.
     *
     * @return True if the database supports DDL within a transaction, otherwise false.
     */
    boolean supportsDDLInTransaction();

    String getDatabaseProductName();

    String getDatabaseProductVersion() throws DatabaseException;

    int getDatabaseMajorVersion() throws DatabaseException;

    int getDatabaseMinorVersion() throws DatabaseException;

    /**
     * Returns an all-lower-case short name of the product.  Used for end-user selecting of database type
     * such as the DBMS precondition.
     */
    String getShortName();

    Integer getDefaultPort();

    /**
     * Returns whether this database support initially deferrable columns.
     */
    boolean supportsInitiallyDeferrableColumns();

    /**
     * Returns database-specific function for generating the current date/time.
     */
    String getCurrentDateTimeFunction();

    void setCurrentDateTimeFunction(String function);

    String getLineComment();

    boolean isSystemObject(ObjectReference example);

    boolean isLiquibaseObject(ObjectReference object);

    String getDateLiteral(java.sql.Date date);

    String getTimeLiteral(java.sql.Time time);

    String getDateTimeLiteral(java.sql.Timestamp timeStamp);

    String getDateLiteral(Date defaultDateValue);

    String escapeObjectName(String objectName, Class<? extends LiquibaseObject> objectType);

    String escapeObjectName(ObjectReference objectReference);

    boolean supportsTablespaces();

    String generatePrimaryKeyName(String tableName);

    void commit() throws DatabaseException;

    void rollback() throws DatabaseException;

    String escapeString(String string);

    void close() throws DatabaseException;

    boolean supportsRestrictForeignKeys();

    boolean isAutoCommit() throws DatabaseException;

    void setAutoCommit(boolean b) throws DatabaseException;

    boolean supportsForeignKeyDisable();

    boolean disableForeignKeyChecks() throws DatabaseException;

    void enableForeignKeyChecks() throws DatabaseException;

    boolean isCaseSensitive(Class<? extends LiquibaseObject> type);

    /**
     * Return true if the database is able to store the given name as is.
     */
    boolean canStoreObjectName(String name, Class<? extends LiquibaseObject> type);

    boolean canStoreObjectName(String name, boolean quoted, Class<? extends LiquibaseObject> type);

    boolean isReservedWord(String string);

    boolean createsIndexesForForeignKeys();

    String escapeDataTypeName(String dataTypeName);

    boolean requiresDefiningColumnsAsNull();

    boolean supportsClustered(Class<? extends LiquibaseObject> objectType);

    boolean supports(Class<? extends LiquibaseObject> type);

    boolean supportsAutoIncrement();

    boolean supportsNamed(Class<? extends LiquibaseObject> type);

    boolean supportsIndexDirection(Index.IndexDirection direction);
}


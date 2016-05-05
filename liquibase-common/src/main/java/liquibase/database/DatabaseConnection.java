package liquibase.database;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.DatabaseException;

import java.sql.Connection;

/**
 * Describes a database connection.
 * We use this interface rather than java.sql.Connection because they do not have to be JDBC-based and may even be an {@link OfflineConnection}
 * 
 */
public interface DatabaseConnection extends ExtensibleObject {

    /**
     * Returns the URL for this connection.
     */
    String getURL();

    /**
     * Returns the username for this connection.
     */
    String getConnectionUserName();

    /**
     * Return true if the connection is read-only
     */
    boolean isReadOnly() throws DatabaseException;

    /**
     * Close the connection. Perform any connection cleanup needed in this method.
     */
    void close() throws DatabaseException;

    /**
     * Returns true if the connection is closed.
     */
    boolean isClosed() throws DatabaseException;

    /**
     * Commits the connection
     */
    void commit() throws DatabaseException;

    /**
     * Rolls back the connection
     */
    void rollback() throws DatabaseException;

    /**
     * Return true if the connection is autocommit
     */
    boolean getAutoCommit() throws DatabaseException;

    /**
     * Sets autocommit mode for the connection.
     *
     * @throws DatabaseException if database does not support transactions.
     */
    void setAutoCommit(boolean autoCommit) throws DatabaseException;

    /**
     * Return the default catalog on the connection, or null if there is none.
     * <br><br>
     * NOTE: The should be the level above what is returned by {@link #getSchema()}. If getSchema returns null, this must return null.
     * This may be different than {@link Connection#getCatalog()} which sometimes calls "schemas" "catalogs"
     *
     * @see liquibase.item.core.Catalog for a discussion on what "catalog" means to Liquibase
     */
    String getCatalog() throws DatabaseException;

    /**
     * Sets the default catalog for this connection.
     * <br><br>
     * NOTE: The should be the level above what is returned by {@link #getSchema()}. If getSchema returns null, this must return null.
     * This may be different than {@link Connection#setCatalog(String)} which sometimes calls "schemas" "catalogs"
     *
     * @see liquibase.item.core.Catalog for a discussion on what "catalog" means to Liquibase
     * @throws DatabaseException if catalog cannot be set.
     */
    void setCatalog(String catalogName) throws DatabaseException;


    /**
     * Return the default schema on the connection, or null if there is none.
     * <br><br>
     * NOTE: The should be the level below what is returned by {@link #getCatalog()}.
     * This may be different than {@link Connection#getSchema()} which sometimes calls "schemas" "catalogs"
     *
     * @see liquibase.item.core.Schema for a discussion on what "schema" means to Liquibase
     */
    String getSchema() throws DatabaseException;

    /**
     * Sets the default schema for this connection.
     * <br><br>
     * NOTE: The should be the level below what is returned by {@link #getCatalog()}.
     * This may be different than {@link Connection#setSchema(String)} which sometimes calls "schemas" "catalogs"
     *
     * @see liquibase.item.core.Schema for a discussion on what "schema" means to Liquibase
     * @throws DatabaseException if the schema cannot be set.
     */
    void setSchema(String schemaName) throws DatabaseException;

    /**
     * Returns a human-readable version of the database product.
     */
    String getDatabaseProductName();

    /**
     * Return a human-readable version of the database version.
     */
    String getDatabaseProductVersion() throws DatabaseException;

    /**
     * Return the database major version. Used for programmatically determining the version for compatibility checks.
     */
    int getDatabaseMajorVersion() throws DatabaseException;

    /**
     * Return the database minor version. Used for programmatically determining the version for compatibility checks.
     */
    int getDatabaseMinorVersion() throws DatabaseException;

    /**
     * Configure whatever the connection can on the given database.
     * Examples include adding more reserved words, setting properties, etc.
     */
    void configureDatabase(Database database, Scope scope);
}

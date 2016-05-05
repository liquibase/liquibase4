package liquibase.database;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * A DatabaseConnection for JDBC databases.
 * Wraps and exposes most java.sql.Connection methods for easier calling and potential logic overriding, so use methods on this object rather than calling {@link #getUnderlyingConnection()} when possible.
 * That being said, to simplify this class, not all java.sql.Connection methods are wrapped, only the major ones Liquibase and extension will probably use.
 */
public class JdbcConnection extends AbstractExtensibleObject implements DatabaseConnection {
    private Connection connection;

    public JdbcConnection(Connection connection) {
        if (connection == null) {
            throw new UnexpectedLiquibaseException("JdbcConnection cannot be passed a null connection");
        }
        this.connection = connection;
    }

    /**
     * Returns the underlying connection wrapped by this class.
     */
    public Connection getUnderlyingConnection() {
        return connection;
    }

    @Override
    public void configureDatabase(Database database, Scope scope) {
        try {
            if (database instanceof AbstractJdbcDatabase) {
                ((AbstractJdbcDatabase) database).reservedWords.addAll(StringUtil.splitAndTrim(getMetaData().getSQLKeywords().toUpperCase(), ","));
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).warn("Error fetching reserved words list from JDBC driver", e);
        }
    }

    @Override
    public String getDatabaseProductName() {
        try {
            return connection.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        try {
            return connection.getMetaData().getDatabaseProductVersion();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        try {
            return connection.getMetaData().getDatabaseMajorVersion();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        try {
            return connection.getMetaData().getDatabaseMinorVersion();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String getURL() {
        try {
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public boolean isReadOnly() throws DatabaseException {
        try {
            return connection.isReadOnly();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    public boolean getAutoCommit() throws DatabaseException {
        try {
            return connection.getAutoCommit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {
        try {
            if (connection.getAutoCommit() != autoCommit) { //sometimes databases don't allow setting autocommit to the same value as before. Ex: sybase jConnect driver throws DatabaseException(JZ016: The AutoCommit option is already set to false)
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    public String getCatalog() throws DatabaseException {
        try {
            return connection.getCatalog();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setCatalog(String catalog) throws DatabaseException {
        try {
            connection.setCatalog(catalog);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    public String getSchema() throws DatabaseException {
        try {
            return connection.getSchema();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setSchema(String schema) throws DatabaseException {
        try {
            connection.setSchema(schema);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    public String getConnectionUserName() {
        try {
            return connection.getMetaData().getUserName();
        } catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public void close() throws DatabaseException {
        rollback();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean isClosed() throws DatabaseException {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void commit() throws DatabaseException {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void rollback() throws DatabaseException {
        try {
            if (!connection.getAutoCommit() && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    public void clearWarnings() throws DatabaseException {
        try {
            connection.clearWarnings();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    public Statement createStatement() throws DatabaseException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public DatabaseMetaData getMetaData() throws DatabaseException {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public int getTransactionIsolation() throws DatabaseException {
        try {
            return connection.getTransactionIsolation();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public SQLWarning getWarnings() throws DatabaseException {
        try {
            return connection.getWarnings();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public CallableStatement prepareCall(String sql) throws DatabaseException {
        try {
            return connection.prepareCall(sql);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws DatabaseException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String toString() {
        return getConnectionUserName() + "@" + getURL();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof JdbcConnection && this.toString().equals(obj.toString());

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

package liquibase.database.core.h2;

import liquibase.Scope;
import liquibase.database.JdbcConnection;
import liquibase.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseConnection extends JdbcConnection {

    @Override
    public int getPriority(ConnectionParameters parameters, Scope scope) {
        if (parameters.url != null && parameters.url.startsWith("jdbc:h2:")) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public String getSchema() throws DatabaseException {
        try (Statement stmt = getUnderlyingConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select SCHEMA()")) {
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}

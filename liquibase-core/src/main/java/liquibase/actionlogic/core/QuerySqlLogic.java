package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.QuerySqlAction;
import liquibase.actionlogic.AbstractSqlLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.JdbcConnection;
import liquibase.exception.ActionPerformException;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QuerySqlLogic extends AbstractSqlLogic<QuerySqlAction> {

    @Override
    protected Class<QuerySqlAction> getSupportedAction() {
        return QuerySqlAction.class;
    }

    @Override
    public ActionResult execute(QuerySqlAction action, Scope scope) throws ActionPerformException {
        String sql = action.sql.toString();
        try {
            LoggerFactory.getLogger(getClass()).info("Querying with SQL: "+sql);

            AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();
            DatabaseConnection connection = database.getConnection();

            Connection jdbcConnection = ((JdbcConnection) connection).getUnderlyingConnection();
            try (Statement stmt = jdbcConnection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                return new RowBasedQueryResult(action, database.extract(rs, scope));
            }
        } catch (Exception e) {
            throw new ActionPerformException("Error executing query '" + sql + "'" + e);
        }
    }
}

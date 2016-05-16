package liquibase.actionlogic.core;

import liquibase.ExecuteMode;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.UpdateSqlAction;
import liquibase.actionlogic.*;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.JdbcConnection;
import liquibase.exception.ActionPerformException;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateSqlLogic extends AbstractSqlLogic<UpdateSqlAction> {

    @Override
    protected Class<UpdateSqlAction> getSupportedAction() {
        return UpdateSqlAction.class;
    }

    @Override
    public ValidationErrors validate(UpdateSqlAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("sql");
    }

    @Override
    public int getPriority(UpdateSqlAction action, Scope scope) {
        if (action instanceof UpdateSqlAction) {
            Database database = scope.getDatabase();
            if (database == null || (!(database instanceof AbstractJdbcDatabase))) {
                return PRIORITY_NOT_APPLICABLE;
            }

            return super.getPriority(action, scope);
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }


    @Override
    public ActionResult execute(UpdateSqlAction action, Scope scope) throws ActionPerformException {
        String sql = action.sql.toString();

        ExecuteMode executeMode = scope.get(Scope.Attr.executeMode, ExecuteMode.READ_WRITE);

        if (executeMode == ExecuteMode.READ_WRITE) {
            AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();
            DatabaseConnection connection = database.getConnection();

            Connection jdbcConnection = ((JdbcConnection) connection).getUnderlyingConnection();
            LoggerFactory.getLogger(getClass()).info("Updating with SQL: "+sql);

            try (Statement stmt = jdbcConnection.createStatement()) {
                int rows = stmt.executeUpdate(sql);

                return new UpdateResult(action, rows);
            } catch (SQLException e) {
                throw new ActionPerformException("Error executing SQL: "+ sql, e);
            }
        } else {
            LoggerFactory.getLogger(getClass()).debug("Not executing update SQL due to running in " + executeMode + " mode: " + sql);
            return new NoOpResult(action, "Update not executed due to running in " + executeMode + " mode");
        }
    }
}

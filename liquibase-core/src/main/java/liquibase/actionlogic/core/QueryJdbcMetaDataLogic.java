package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.JdbcConnection;
import liquibase.exception.ActionPerformException;
import liquibase.exception.DatabaseException;
import liquibase.ValidationErrors;
import liquibase.util.JdbcUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Executes a method on {@link DatabaseMetaData}.
 * No pre-processing of the arguments is performed. No post-processing of the results is performed.
 */
public class QueryJdbcMetaDataLogic extends AbstractActionLogic<QueryJdbcMetaDataAction> {

    @Override
    protected Class<QueryJdbcMetaDataAction> getSupportedAction() {
        return QueryJdbcMetaDataAction.class;
    }

    @Override
    public ValidationErrors validate(QueryJdbcMetaDataAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("method");

        if (!errors.hasErrors()) {
            if (action.method.equals("getTables")) {
                if (action.arguments.size() != 4) {
                    errors.addError("getTables requires 4 arguments");
                }
            } else if (action.method.equals("getColumns")) {
                if (action.arguments.size() != 4) {
                    errors.addError("getColumns requires 4 arguments");
                }
            } else if (action.method.equals("getPrimaryKeys")) {
                if (action.arguments.size() != 3) {
                    errors.addError("getPrimaryKeys requires 3 arguments");
                } else {
                    if (action.arguments.size() > 2 && action.arguments.get(2) == null) {
                        errors.addError("getPrimaryKeys requires a not-null 3rd argument (table name)");
                    }
                }
            } else if (action.method.equals("getImportedKeys")) {
                if (action.arguments.size() != 3) {
                    errors.addError("getImportedKeys requires 3 arguments");
                } else {
                    if (action.arguments.size() > 2 && action.arguments.get(2) == null) {
                        errors.addError("getImportedKeys requires a 3rd argument (table name)");
                    }
                }
            } else if (action.method.equals("getIndexInfo")) {
                if (action.arguments.size() != 5) {
                    errors.addError("getIndexInfo requires 5 arguments");
                } else {
                    if (action.arguments.get(2) == null) {
                        errors.addError("getIndexInfo requires a 3rd argument (table name)");
                    }
                }
            } else {
                errors.addError("Unknown method '" + action.method + "' for validation");
            }
        }
        return errors;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        Database database = scope.getDatabase();

        return super.supportsScope(scope) && database instanceof AbstractJdbcDatabase;
    }

    @Override
    public boolean executeInteractsExternally(QueryJdbcMetaDataAction action, Scope scope) {
        return true;
    }

    @Override
    public ActionResult execute(QueryJdbcMetaDataAction action, Scope scope) throws ActionPerformException {
        String method = action.method;
        List arguments = action.arguments;
        try {
            if (method.equals("getTables")) {
                return new RowBasedQueryResult(action, JdbcUtils.extract(getMetaData(scope).getTables((String) arguments.get(0), (String) arguments.get(1), (String) arguments.get(2), (String[]) arguments.get(3))));
            } else if (method.equals("getColumns")) {
                return new RowBasedQueryResult(action, JdbcUtils.extract(getMetaData(scope).getColumns((String) arguments.get(0), (String) arguments.get(1), (String) arguments.get(2), (String) arguments.get(3))));
            } else if (method.equals("getPrimaryKeys")) {
                return new RowBasedQueryResult(action, JdbcUtils.extract(getMetaData(scope).getPrimaryKeys((String) arguments.get(0), (String) arguments.get(1), (String) arguments.get(2))));
            } else if (method.equals("getImportedKeys")) {
                return new RowBasedQueryResult(action, JdbcUtils.extract(getMetaData(scope).getImportedKeys((String) arguments.get(0), (String) arguments.get(1), (String) arguments.get(2))));
            } else if (method.equals("getIndexInfo")) {
                return new RowBasedQueryResult(action, JdbcUtils.extract(getMetaData(scope).getIndexInfo((String) arguments.get(0), (String) arguments.get(1), (String) arguments.get(2), (Boolean) arguments.get(3), (Boolean) arguments.get(4))));
            }
            throw new ActionPerformException("Unknown method '" + method + "'");
        } catch (Exception e) {
            throw new ActionPerformException(e);
        }
    }

    protected DatabaseMetaData getMetaData(Scope scope) throws DatabaseException {
        AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();
        Connection underlyingConnection = ((JdbcConnection) database.getConnection()).getUnderlyingConnection();

        try {
            return underlyingConnection.getMetaData();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


}

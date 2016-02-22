package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.ValidationErrors;

/**
 * Convenience base class for {@link liquibase.actionlogic.ActionLogic} implementations.
 */
public abstract class AbstractActionLogic<ActionType extends Action> implements ActionLogic<ActionType> {

    /**
     * Returns the Action class supported by this ActionLogic implementation. Used by {@link AbstractActionLogic#getPriority(liquibase.action.Action, liquibase.Scope)}
     */
    protected abstract Class<? extends ActionType> getSupportedAction();

    /**
     * Return the type of {@link Database} this this ActionLogic requires.
     * Should return liquibase.database.Database if any database is supported, but one is required (default).
     * Return null if no database is required.
     * Used by {@link #supportsScope(liquibase.Scope)}. If more complex logic is required than just one Database subclass, override supportsScope(Scope).
     */
    protected Class<? extends Database> getRequiredDatabase() {
        return Database.class;
    }

    /**
     * Return the type of {@link liquibase.database.DatabaseConnection} this this ActionLogic requires.
     * Return liquibase.database.DatabaseConnection if any database is supported, but one is required (default).
     * Return null if no database is required.
     * Used by {@link #supportsScope(liquibase.Scope)}. If more complex logic is required than just one DatabaseConnection subclass, override supportsScope(Scope).
     */
    protected Class<? extends DatabaseConnection> getRequiredConnection() {
        return DatabaseConnection.class;
    }

    /**
     * Return true this ActionLogic implementation is valid for the given scope. Used by {@link AbstractActionLogic#getPriority(liquibase.action.Action, liquibase.Scope)}
     */
    protected boolean supportsScope(Scope scope) {
        Class<? extends Database> requiredDatabase = getRequiredDatabase();
        if (requiredDatabase != null) {
            Database database = scope.getDatabase();
            boolean databaseCorrect = database != null && requiredDatabase.isAssignableFrom(database.getClass());

            if (databaseCorrect) {
                Class<? extends DatabaseConnection> requiredConnection = getRequiredConnection();

                if (requiredConnection != null) {
                    DatabaseConnection connection = database.getConnection();
                    databaseCorrect = connection != null && requiredConnection.isAssignableFrom(connection.getClass());
                }
            }
            return databaseCorrect;
        }

        return true;
    }

    /**
     * Implementation relies on {@link #getSupportedAction()} and {@link #supportsScope(Scope)} to determine the priority to return.
     * Normally, you should only need to override one or both of those methods and not override this. It is not final, however, so it can be overridden if need be.
     */
    @Override
    public int getPriority(ActionType action, Scope scope) {
        if (!action.getClass().isAssignableFrom(getSupportedAction())) {
            return PRIORITY_NOT_APPLICABLE;
        }
        if (!supportsScope(scope)) {
            return PRIORITY_NOT_APPLICABLE;
        }

        Class<? extends Database> requiredDatabase = getRequiredDatabase();
        if (requiredDatabase == null || requiredDatabase.equals(Database.class)) {
            return PRIORITY_DEFAULT;
        } else {
            return PRIORITY_SPECIALIZED;
        }
    }

    /**
     * Standard implementation returns an empty ValidationErrors
     */
    @Override
    public ValidationErrors validate(ActionType action, Scope scope) {
        return new ValidationErrors(action);
    }

    /**
     * Default implementation returns an {@link liquibase.action.ActionStatus.Status#unknown} message of "No checkStatus logic defined".
     */
    @Override
    public ActionStatus checkStatus(ActionType action, Scope scope) {
        return new ActionStatus().add(ActionStatus.Status.unknown, "No checkStatus logic defined");
    }

    /**
     * Default implementation returns "false"
     */
    @Override
    public boolean executeInteractsExternally(ActionType action, Scope scope) {
        return false;
    }
}

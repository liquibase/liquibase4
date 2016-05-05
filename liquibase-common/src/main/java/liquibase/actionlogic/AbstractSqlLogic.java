package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.database.AbstractJdbcDatabase;

/**
 * Base class for {@link ActionLogic} implementations that execute SQL.
 * Most implementations should not extend this because they should not actually execute SQL.
 * Instead they should extends {@link liquibase.actionlogic.AbstractSqlBuilderLogic} which returns a {@link liquibase.actionlogic.DelegateResult} that contains a {@link liquibase.action.ExecuteSqlAction} which will be handled by a subclass of this.
 */
public abstract class AbstractSqlLogic<ActionType extends Action> extends AbstractActionLogic<ActionType>  {

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase() instanceof AbstractJdbcDatabase;
    }

    @Override
    public boolean executeInteractsExternally(Action action, Scope scope) {
        return true;
    }
}

package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.action.ExecuteSqlAction;
import liquibase.exception.ActionPerformException;
import liquibase.util.StringClauses;

/**
 * Convenience base class for ActionLogic implementations that generate SQL.
 */
public abstract class AbstractSqlBuilderLogic<ActionType extends AbstractAction> extends AbstractActionLogic<ActionType> {

    /**
     * Return the SQL to be executed. It is up to the implementation to break up the SQL into {@link StringClauses} that are most helpful subclasses or other code to use.
     * Database-specific subclasses should normally call super.generateSql(action, scope) and then modify the returned StringClauses rather than rewriting the generate logic.
     * To help classes that use your generated SQL, consider including an enum of clauses which can be used to more safely find pieces of your SQL for changing or reuse.
     * <br><br>
     * If you override {@link #execute(AbstractAction, Scope)} to use something like {@link liquibase.action.core.AlterTableAction}, the output from
     * this method would include just the definition that goes into AlterTableAction.
     */
    protected abstract StringClauses generateSql(ActionType action, Scope scope);

    /**
     * Wraps the output of {@link #generateSql(AbstractAction, Scope)} in a {@link DelegateResult} containing an {@link ExecuteSqlAction}.
     */
    @Override
    public ActionResult execute(ActionType action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new ExecuteSqlAction(generateSql(action, scope).toString()));
    }

}

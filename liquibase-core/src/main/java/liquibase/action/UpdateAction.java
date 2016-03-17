package liquibase.action;

/**
 * Describes an {@link liquibase.action.Action} that updates data in a database or any other location.
 * The logic for the update goes in an implementation of {@link liquibase.actionlogic.ActionLogic}
 *
 * @see liquibase.action.core.UpdateDataAction for cross-database updates
 * @see UpdateSqlAction for SQL-based updates
 */
public interface UpdateAction extends Action {

}

package liquibase.action;

/**
 * Describes an {@link liquibase.action.Action} that fetches data from a database or any other location.
 * The logic for the query goes in an implementation of {@link liquibase.actionlogic.ActionLogic}
 *
 * @see QuerySqlAction for SQL-based queries
 */
public interface QueryAction extends Action {

}

package liquibase.actionlogic;

import liquibase.action.Action;

import java.util.List;

/**
 * Result of an action that queries existing data.
 * For database or other row-based data, use {@link liquibase.actionlogic.RowBasedQueryResult}
 */
public abstract class QueryResult extends ActionResult {

    public QueryResult(Action sourceAction) {
        super(sourceAction);
    }

    public QueryResult(String message, Action sourceAction) {
        super(message, sourceAction);
    }

    /**
     * Return a single object of the given type.
     */
    public abstract <T> T asObject(Class<T> requiredType) throws IllegalArgumentException;

    /**
     * Returns a single object of the given type. Returns the passed defaultValue if the value is null
     */
    public abstract <T> T asObject(T defaultValue) throws IllegalArgumentException;

    /**
     * Return a list of objects of the given type.
     */
    public abstract <T> List<T> asList(Class<T> elementType) throws IllegalArgumentException;

    public abstract int size();
}

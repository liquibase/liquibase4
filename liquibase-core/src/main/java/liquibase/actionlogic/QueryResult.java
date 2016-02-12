package liquibase.actionlogic;

import liquibase.action.Action;

import java.util.List;

/**
 * Result of an action that queries existing data.
 * For row-based data, use {@link RowBasedQueryResult}.
 * For data that is objects, use {@link ObjectBasedQueryResult}
 */
public abstract class QueryResult extends ActionResult {

    public QueryResult(Action sourceAction) {
        super(sourceAction);
    }

    public QueryResult(Action sourceAction, String message) {
        super(sourceAction, message);
    }

    /**
     * Return a single object of the given type.
     * If this result is of a different type, convert it to the required type or throw IllegalArgumentException if it cannot be converted.
     * If this result contains more than one object, IllegalArgumentException.
     */
    public abstract <T> T asObject(Class<T> requiredType) throws IllegalArgumentException;

    /**
     * Returns a single object of the same type as defaultValue. If {@link #asObject(Class)} returns null, return the defaultValue instead.
     */
    public <T> T asObject(T defaultValue) throws IllegalArgumentException {
        T obj = (T) asObject(defaultValue.getClass());
        if (obj == null) {
            return defaultValue;
        }
        return obj;
    }

    /**
     * Return a list of objects of the given type.
     * If this result is of a different type, convert it to the required type or throw IllegalArgumentException if it cannot be converted.
     */
    public abstract <T> List<T> asList(Class<T> elementType) throws IllegalArgumentException;

    /**
     * Returns the size of this result.
     */
    public abstract int size();
}

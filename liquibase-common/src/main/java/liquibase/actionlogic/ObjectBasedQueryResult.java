package liquibase.actionlogic;

import liquibase.action.Action;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.ObjectUtil;

import java.sql.ResultSet;
import java.util.*;

/**
 * A {@link QueryResult} that contains object(s).
 * If there is just one object, you can either use {@link #asObject(Class)} or {@link #asList(Class)} but if there are more than one object, you must use {@link #asList(Class)}.
 * If the result is a collection of rows, not objects use {@link RowBasedQueryResult}
 */
public class ObjectBasedQueryResult extends QueryResult {

    private List resultSet;

    public ObjectBasedQueryResult(Action sourceAction, Object result) {
        this(sourceAction, null, result);
    }

    public ObjectBasedQueryResult(Action sourceAction, String message, Object result) {
        super(sourceAction, message);
        if (result == null) {
            this.resultSet = Collections.unmodifiableList(new ArrayList());
            return;
        }

        if (result instanceof ResultSet) {
            throw new UnexpectedLiquibaseException("Cannot pass ResultSet directly into ObjectBasedQueryResult. Use JdbcUtils.extract() to create a disconnected collection to pass in.");
        }

        if (!(result instanceof Collection)) {
            result = Arrays.asList(result);
        }

        this.resultSet = Collections.unmodifiableList((List) result);
    }

    @Override
    public <T> T asObject(Class<T> requiredType) throws IllegalArgumentException {
        return ObjectUtil.convert(getSingleEntry(), requiredType);
    }

    @Override
    public <T> List<T> asList(Class<T> elementType) throws IllegalArgumentException {
        List returnList = new ArrayList();
        for (Object obj : resultSet) {
            returnList.add(ObjectUtil.convert(obj, elementType));
        }
        return Collections.unmodifiableList(returnList);

    }

    protected Object getSingleEntry() throws IllegalArgumentException {
        if (resultSet.size() == 0) {
            return null;
        }
        if (resultSet.size() > 1) {
            throw new IllegalArgumentException("Results contained " + resultSet.size() + " rows");
        }
        return resultSet.get(0);
    }

    @Override
    public int size() {
        return resultSet.size();
    }
}

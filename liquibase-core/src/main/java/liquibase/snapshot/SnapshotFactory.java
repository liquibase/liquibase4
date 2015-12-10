package liquibase.snapshot;

import liquibase.Scope;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.actionlogic.QueryResult;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

import java.util.List;

public class SnapshotFactory {

    protected SnapshotFactory() {
    }

    public boolean has(ObjectReference object, Scope scope) throws ActionPerformException {
        QueryResult result = (QueryResult) new ActionExecutor().execute(new SnapshotObjectsAction(object), scope);
        return result.size() > 0;
    }

    public <T extends LiquibaseObject> T snapshot(ObjectReference object, Scope scope) throws ActionPerformException {
        QueryResult result = (QueryResult) new ActionExecutor().execute(new SnapshotObjectsAction(object), scope);

        return (T) result.asObject(object.type);
    }


    public <T extends LiquibaseObject> T snapshot(Class<T> type, ObjectReference relatedTo, Scope scope) throws ActionPerformException {
        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);

        return actionExecutor.query(new SnapshotObjectsAction(type, relatedTo), scope).asObject(type);
    }

    /**
     * Snapshotting multiple objects. Returns empty list if none are found.
     */
    public <T extends LiquibaseObject> List<T> snapshotAll(Class<T> type, ObjectReference relatedTo, Scope scope) throws ActionPerformException {
        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);

        return actionExecutor.query(new SnapshotObjectsAction(type, relatedTo), scope).asList(type);
    }

}

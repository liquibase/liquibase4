package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.exception.ActionPerformException;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Set;

public abstract class AbstractSnapshotObjectsLogicOffline<T extends SnapshotObjectsAction> extends AbstractSnapshotObjectsLogic<T> {

    @Override
    protected Class<? extends DatabaseConnection> getRequiredConnection() {
        return OfflineConnection.class;
    }


    @Override
    public ActionResult execute(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        final Database database = scope.getDatabase();
        OfflineConnection connection = (OfflineConnection) database.getConnection();
        Snapshot snapshot = connection.getSnapshot();

        if (snapshot == null) {
            throw new ActionPerformException("No snapshot found");
        }

        Set allObjectsOfType = snapshot.get(getTypeToSnapshot());


        return new ObjectBasedQueryResult(CollectionUtil.select(new ArrayList(allObjectsOfType), getDatabaseObjectFilter(relatedTo, action, scope)), action);
    }

    protected abstract CollectionUtil.CollectionFilter<? extends LiquibaseObject> getDatabaseObjectFilter(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope);


}

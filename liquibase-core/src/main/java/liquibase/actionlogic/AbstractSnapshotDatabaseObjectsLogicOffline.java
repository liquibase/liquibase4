package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.snapshot.Snapshot;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base class for snapshotting database objects when using a database with an {@link OfflineConnection}.
 */
public abstract class AbstractSnapshotDatabaseObjectsLogicOffline<ObjectType extends Item> extends AbstractSnapshotItemsLogic<SnapshotItemsAction, ObjectType> {

    @Override
    protected Class<? extends DatabaseConnection> getRequiredConnection() {
        return OfflineConnection.class;
    }

    /**
     * Takes the {@link Snapshot} object off the OfflineConnection and then uses {@link #isRelatedTo(Item, DatabaseObjectReference, SnapshotItemsAction, Scope)} to filter down
     * the list of objects to only the correct ones.
     * Normally you don't need to override this method, only getDatabaseObjectsFilter.
     * Throws ActionPerformException if there is no snapshot on the connection.
     */
    @Override
    public ActionResult execute(final ItemReference relatedTo, final SnapshotItemsAction action, final Scope scope) throws ActionPerformException {
        final Database database = scope.getDatabase();
        OfflineConnection connection = (OfflineConnection) database.getConnection();
        Snapshot snapshot = connection.getSnapshot();

        if (snapshot == null) {
            throw new ActionPerformException("No snapshot found");
        }

        Collection allObjectsOfType = snapshot.get(getTypeToSnapshot());


        return new ObjectBasedQueryResult(action, CollectionUtil.select(new ArrayList(allObjectsOfType), new CollectionUtil.CollectionFilter<ObjectType>() {
            @Override
            public boolean include(ObjectType obj) {
                return isRelatedTo(obj, (DatabaseObjectReference) relatedTo, action, scope);
            }
        }));
    }

    /**
     * This m ethod is used by {@link AbstractSnapshotItemsLogic#execute(ItemReference, SnapshotItemsAction, Scope)} to filter down all objects of the {@link #getTypeToSnapshot()}
     * to only the ones that are related to relatedTo.
     */
    protected abstract boolean isRelatedTo(ObjectType objectToCheck, DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope);


}

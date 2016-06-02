package liquibase.snapshot;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.actionlogic.QueryResult;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.ActionPerformException;
import liquibase.exception.DatabaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * Class for creating {@link Snapshot} objects, as well as convenience methods for snapshotting individual objects.
 * Methods take {@link Scope} rather than {@link liquibase.database.Database} parameters, because the class is designed to support
 * snapshotting other environments such as server configurations down the road without breaking the snapshot API.
 * The methods will look for the needed objects such as {@link liquibase.database.Database} within the passed scope.
 */
public class SnapshotFactory implements SingletonObject {

    protected SnapshotFactory() {
    }

    /**
     * Return true if the given {@link ItemReference} exists based on the scope.
     */
    public boolean has(ItemReference reference, Scope scope) throws ActionPerformException {
        QueryResult result = (QueryResult) scope.getSingleton(ActionExecutor.class).execute(new SnapshotItemsAction(reference), scope);
        return result.size() > 0;
    }


    /**
     * Creates a full snapshot of the given scope using the passed relatedObjects and types to control what is snapshotted.
     * Includes populating database information fields if applicable.
     */
    public Snapshot snapshot(Set<ItemReference> relatedObjects, Set<Class<? extends Item>> types, Scope scope) throws ActionPerformException {
        Snapshot snapshot = new Snapshot(scope);
        for (ItemReference related : relatedObjects) {
            for (Class type : types) {
                snapshot.addAll(scope.getSingleton(SnapshotFactory.class).snapshotAll(type, related, scope));
            }
        }

        Database database = scope.getDatabase();
        if (database != null) {
            DatabaseConnection connection = database.getConnection();

            if (connection != null) {
                try {
                    snapshot.databaseProductName = connection.getDatabaseProductName();
                    snapshot.databaseProductVersion = connection.getDatabaseProductVersion();
                    snapshot.databaseMajorVersion = connection.getDatabaseMajorVersion();
                    snapshot.databaseMinorVersion = connection.getDatabaseMinorVersion();
                    snapshot.databaseUrl = connection.getURL();
                    snapshot.databaseUsername = connection.getConnectionUserName();
                    snapshot.databaseDefaultCatalog = connection.getCatalog();
                    snapshot.databaseDefaultSchema = connection.getSchema();
                } catch (DatabaseException e) {
                    LoggerFactory.getLogger(getClass()).error("Could not read databaseInformation", e);
                }
            }
        }
        return snapshot;

    }

    /**
     * Return a single object based on the scope. Returns null if the object does not exist.
     */
    public <T extends Item> T snapshot(ItemReference reference, Scope scope) throws ActionPerformException {
        QueryResult result = (QueryResult) scope.getSingleton(ActionExecutor.class).execute(new SnapshotItemsAction(reference), scope);

        return (T) result.asObject(reference.type);
    }


    /**
     * Returns a single object object of the given type that is related to the given {@link ItemReference}. Return null if none is found.
     */
    public <T extends Item> T snapshot(Class<T> type, ItemReference relatedTo, Scope scope) throws ActionPerformException {
        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);

        return actionExecutor.query(new SnapshotItemsAction(type, relatedTo), scope).asObject(type);
    }

    /**
     * Returns all objects of the given type that are related to the {@link ItemReference}. Returns empty collection if none are found.
     */
    public <T extends Item> Collection<T> snapshotAll(Class<T> type, ItemReference relatedTo, Scope scope) throws ActionPerformException {
        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);

        return actionExecutor.query(new SnapshotItemsAction(type, relatedTo), scope).asList(type);
    }
}

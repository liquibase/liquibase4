package liquibase.command;

import liquibase.Scope;
import liquibase.exception.DatabaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.SchemaReference;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

public abstract class AbstractSnapshotCommand<T extends CommandResult> extends AbstractDatabaseCommand<T> {

    public Set<ItemReference> relatedObjects = new HashSet<>();

    public AbstractSnapshotCommand() {
    }

    public AbstractSnapshotCommand(ItemReference... relatedObjects) {

    }


    protected Snapshot snapshot(Scope scope) throws DatabaseException, liquibase.exception.ActionPerformException {
        Set<Class<? extends Item>> types = getTypesToSnapshot(scope);
        Set<ItemReference> relatedObjects = getRelatedObjectsForSnapshot(scope);


        return scope.getSingleton(SnapshotFactory.class).snapshot(relatedObjects, types, scope);

    }

    protected Set<ItemReference> getRelatedObjectsForSnapshot(Scope scope) throws DatabaseException {
        Set<ItemReference> relatedObjects = new HashSet<>(this.relatedObjects);
        if (relatedObjects.size() == 0) {
            SchemaReference schema = new SchemaReference(scope.getDatabase().getConnection().getSchema());
            relatedObjects.add(schema);
        }
        return relatedObjects;
    }

    protected Set<Class<? extends Item>> getTypesToSnapshot(Scope scope) {
        Set<Class<? extends Item>> types = new HashSet<>();

        for (Item item : ServiceLoader.load(Item.class, scope.getClassLoader(true))) {
            if (scope.getDatabase().supports(item.getClass(), scope)) {
                types.add(item.getClass());
            }
        }
        return types;
    }
}

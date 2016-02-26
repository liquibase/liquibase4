package liquibase.snapshot;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtils;

import java.util.*;

/**
 * Encapsulates information snapshotted.
 * Currently Liquibase only supports snapshotting databases, but the classes are designed to be extensible to someday support other things, such as server configurations.
 *
 * @see SnapshotFactory
 */
public class Snapshot extends AbstractExtensibleObject {

    private final Scope scopeCreatedUnder;

    //objects in snapshot, grouped by the type of object
    private Map<Class<? extends Item>, List<? extends Item>> objects = new HashMap<>();

    /**
     * Creates an empty Snapshot.
     *
     * @param scopeCreatedUnder is the scope at the point this snapshot was created. Should include the database and any other settings applicable to the snapshot.
     */
    public Snapshot(Scope scopeCreatedUnder) {
        this.scopeCreatedUnder = scopeCreatedUnder;
    }

    public Scope getScopeCreatedUnder() {
        return scopeCreatedUnder;
    }

    public String describe() {
        return "Snapshot(" + StringUtils.join(objects, ", ") + ")";
    }

    /**
     * Adds the given object to this snapshot.
     */
    public Snapshot add(Item object) {
        List typeObjects = this.objects.get(object.getClass());
        if (typeObjects == null) {
            typeObjects = new ArrayList<>();
            this.objects.put(object.getClass(), typeObjects);
        }

        typeObjects.add(object);

        return this;
    }

    /**
     * Convenience method to call {@link #add(Item)} for all objects in a collection.
     */
    public Snapshot addAll(Collection<? extends Item> objects) {
        for (Item obj : objects) {
            add(obj);
        }
        return this;
    }

    /**
     * Returns all the objects in the snapshot of the given type.
     */
    public <T extends Item> Collection<T> get(Class<T> type) {
        return Collections.unmodifiableCollection(CollectionUtil.createIfNull((List<T>) objects.get(type)));
    }

    /**
     * Returns all the objects in this snapshot that (fuzzily) match the passed {@link ItemReference}.
     * Returns empty collection if none match.
     */
    public <T extends Item> Collection<T> getAll(Class<T> type, final ItemReference reference) {
        return CollectionUtil.select(CollectionUtil.createIfNull((List<T>) objects.get(type)),
                new CollectionUtil.CollectionFilter<T>() {
                    @Override
                    public boolean include(Item obj) {
                        return obj.toReference().equals(reference, true);
                    }
                });

    }

    /**
     * Returns the object with a {@link Item#toReference()} (fuzzily) matching the passed {@link ItemReference}, or null if the object is not in the snapshot.
     * If more than one object in the snapshot matches, an {@link liquibase.exception.UnexpectedLiquibaseException} exception is thrown.
     */
    public <T extends Item> T get(Class<T> type, ItemReference reference) {
        Collection<T> all = getAll(type, reference);
        if (all.size() == 0) {
            return null;
        } else if (all.size() == 1) {
            return all.iterator().next();
        } else {
            throw new UnexpectedLiquibaseException("Found multiple objects in snapshot matching " + reference);
        }
    }
}

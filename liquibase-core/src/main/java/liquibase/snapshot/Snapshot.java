package liquibase.snapshot;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
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
    private Map<Class<? extends LiquibaseObject>, List<? extends LiquibaseObject>> objects = new HashMap<>();

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
    public Snapshot add(LiquibaseObject object) {
        List typeObjects = this.objects.get(object.getClass());
        if (typeObjects == null) {
            typeObjects = new ArrayList<>();
            this.objects.put(object.getClass(), typeObjects);
        }

        typeObjects.add(object);

        return this;
    }

    /**
     * Convenience method to call {@link #add(LiquibaseObject)} for all objects in a collection.
     */
    public Snapshot addAll(Collection<? extends LiquibaseObject> objects) {
        for (LiquibaseObject obj : objects) {
            add(obj);
        }
        return this;
    }

    /**
     * Returns all the objects in the snapshot of the given type.
     */
    public <T extends LiquibaseObject> Collection<T> get(Class<T> type) {
        return Collections.unmodifiableCollection(CollectionUtil.createIfNull((List<T>) objects.get(type)));
    }

    /**
     * Returns all the objects in this snapshot that (fuzzily) match the passed objectReference.
     * Returns empty collection if none match.
     */
    public <T extends LiquibaseObject> Collection<T> getAll(Class<T> type, final ObjectReference objectReference) {
        return CollectionUtil.select(CollectionUtil.createIfNull((List<T>) objects.get(type)),
                new CollectionUtil.CollectionFilter<T>() {
                    @Override
                    public boolean include(LiquibaseObject obj) {
                        return obj.toReference().equals(objectReference, true);
                    }
                });

    }

    /**
     * Returns the object with a {@link LiquibaseObject#toReference()} (fuzzily) matching the passed ObjectReference, or null if the object is not in the snapshot.
     * If more than one object in the snapshot matches, an {@link liquibase.exception.UnexpectedLiquibaseException} exception is thrown.
     */
    public <T extends LiquibaseObject> T get(Class<T> type, ObjectReference reference) {
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

package liquibase.snapshot;

import liquibase.Scope;
import liquibase.structure.DatabaseObject;
import liquibase.structure.ObjectReference;
import liquibase.util.CollectionUtil;

import java.util.*;

public class Snapshot {

    private final SnapshotIdService snapshotIdService;
    private final Scope scope;
    private Map<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> objects = new HashMap<>();

    public Snapshot(Scope scope) {
        this.snapshotIdService = scope.getSingleton(SnapshotIdService.class);
        this.scope = scope;
    }

    public Scope getScope() {
        return scope;
    }

    public Snapshot add(DatabaseObject object) {
        Set<DatabaseObject> typeObjects = (Set<DatabaseObject>) this.objects.get(object.getClass());
        if (typeObjects == null) {
            typeObjects = new HashSet<>();
            this.objects.put(object.getClass(), typeObjects);
        }

        if (object.getSnapshotId() == null) {
            object.set("snapshotId", snapshotIdService.generateId());
        }
        typeObjects.add(object);

        return this;
    }

    public Snapshot addAll(Collection<? extends DatabaseObject> objects) {
        for (DatabaseObject obj : objects) {
            add(obj);
        }
        return this;
    }

    public <T extends DatabaseObject> T get(Class<T> type, ObjectReference name) {
        Set<T> objects = get(type);
        for (T obj : objects) {
            if (obj.getName().equals(name)) {
                return (T) obj;
            }
        }
        return null;
    }

    public <T extends DatabaseObject> Set<T> get(Class<T> type) {
        return (Set<T>) Collections.unmodifiableSet(CollectionUtil.createIfNull(objects.get(type)));
    }

    public <T extends DatabaseObject> Set<T> getAll(Class<T> type, final ObjectReference objectReference) {
        return CollectionUtil.select(CollectionUtil.createIfNull(objects.get(type)),
                new CollectionUtil.CollectionFilter() {
                    @Override
                    public boolean include(Object obj) {
                        ObjectReference name = ((DatabaseObject) obj).toReference();
                        return name.matches(objectReference);
                    }
                });

    }

    public <T extends DatabaseObject> T get(T example) {
        Set<T> typeObjects = (Set<T>) objects.get(example.getClass());
        if (typeObjects == null) {
            return null;
        }

        for (DatabaseObject obj : typeObjects) {
            if (obj.equals(example)) {
                return (T) obj;
            }
        }
        return null;
    }

}

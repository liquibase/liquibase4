package liquibase.snapshot;

import liquibase.Scope;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.util.CollectionUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtils;

import java.util.*;

public class Snapshot {

    private final SnapshotIdService snapshotIdService;
    private final Scope scope;
    private Map<Class<? extends LiquibaseObject>, Set<? extends LiquibaseObject>> objects = new HashMap<>();

    public Snapshot(Scope scope) {
        this.snapshotIdService = scope.getSingleton(SnapshotIdService.class);
        this.scope = scope;
    }

    public Scope getScope() {
        return scope;
    }

    public String describe() {
        return "Snapshot("+ StringUtils.join(objects, ", ")+")";
    }

    public Snapshot add(LiquibaseObject object) {
        Set<LiquibaseObject> typeObjects = (Set<LiquibaseObject>) this.objects.get(object.getClass());
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

    public Snapshot addAll(Collection<? extends LiquibaseObject> objects) {
        for (LiquibaseObject obj : objects) {
            add(obj);
        }
        return this;
    }

    public <T extends LiquibaseObject> T get(Class<T> type, ObjectReference name) {
        Set<T> objects = get(type);
        for (T obj : objects) {
            if (obj.getName().equals(name)) {
                return (T) obj;
            }
        }
        return null;
    }

    public <T extends LiquibaseObject> Set<T> get(Class<T> type) {
        return (Set<T>) Collections.unmodifiableSet(CollectionUtil.createIfNull(objects.get(type)));
    }

    public <T extends LiquibaseObject> Set<T> getAll(Class<T> type, final ObjectReference objectReference) {
        return CollectionUtil.select(CollectionUtil.createIfNull(objects.get(type)),
                new CollectionUtil.CollectionFilter() {
                    @Override
                    public boolean include(Object obj) {
                        ObjectReference name = ((LiquibaseObject) obj).toReference();
                        return name.equals(objectReference, true);
                    }
                });

    }

    public <T extends LiquibaseObject> T get(T example) {
        Set<T> typeObjects = (Set<T>) objects.get(example.getClass());
        if (typeObjects == null) {
            return null;
        }

        for (LiquibaseObject obj : typeObjects) {
            if (obj.equals(example)) {
                return (T) obj;
            }
        }
        return null;
    }

}

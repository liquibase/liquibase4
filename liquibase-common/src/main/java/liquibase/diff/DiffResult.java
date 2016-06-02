package liquibase.diff;

import liquibase.AbstractExtensibleObject;
import liquibase.item.Item;

import java.util.*;

public class DiffResult extends AbstractExtensibleObject {

    public SortedSet<Class<? extends Item>> comparedTypes;

    private Map<String, StringDiff> snapshotDifferences = new HashMap<>();

    private Set<Item> missingObjects = new HashSet<>();
    private Set<Item> unexpectedObjects = new HashSet<>();
    private Map<Item, ObjectDifferences> changedObjects = new HashMap<>();

    public DiffResult() {
        this.comparedTypes = new TreeSet<>(new Comparator<Class<? extends Item>>() {
            @Override
            public int compare(Class<? extends Item> o1, Class<? extends Item> o2) {
                return o1.getSimpleName().compareTo(o2.getSimpleName());
            }
        });

    }

    public void addSnapshotDifference(String attribute, StringDiff differences) {
        this.snapshotDifferences.put(attribute, differences);
    }

    public StringDiff getSnapshotDifference(String attribute) {
        return this.snapshotDifferences.get(attribute);
    }

    public void addMissing(Item referenceItem) {
        this.missingObjects.add(referenceItem);
    }

    public <T extends Item> Set<T> getMissingObjects(Class<T> type) {
        Set returnSet = new HashSet();
        for (Item obj : missingObjects) {
            if (type.isAssignableFrom(obj.getClass())) {
                returnSet.add(obj);
            }
        }
        return returnSet;
    }

    public <T extends Item> SortedSet<T> getMissingObjects(Class<T> type, Comparator<Item> comparator) {
        TreeSet<T> set = new TreeSet<>(comparator);
        set.addAll(getMissingObjects(type));
        return set;
    }

    public void addChanged(Item currentVersion, ObjectDifferences differences) {
        this.changedObjects.put(currentVersion, differences);
    }

    public void addUnexpected(Item currentItem) {
        this.unexpectedObjects.add(currentItem);
    }

    public <T extends Item> Set<T> getUnexpectedObjects(Class<T> type) {
        Set returnSet = new HashSet();
        for (Item obj : unexpectedObjects) {
            if (type.isAssignableFrom(obj.getClass())) {
                returnSet.add(obj);
            }
        }
        return returnSet;
    }

    public <T extends Item> SortedSet<T> getUnexpectedObjects(Class<T> type, Comparator<Item> comparator) {
        TreeSet<T> set = new TreeSet<>(comparator);
        set.addAll(getUnexpectedObjects(type));
        return set;
    }

    public <T extends Item> Map<T, ObjectDifferences> getChangedObjects(Class<T> type) {
        Map returnSet = new HashMap();
        for (Map.Entry<Item, ObjectDifferences> obj : changedObjects.entrySet()) {
            if (type.isAssignableFrom(obj.getKey().getClass())) {
                returnSet.put(obj.getKey(), obj.getValue());
            }
        }
        return returnSet;
    }

    public <T extends Item> SortedMap<T, ObjectDifferences> getChangedObjects(Class<T> type, Comparator<Item> comparator) {
        SortedMap<T, ObjectDifferences> map = new TreeMap<>(comparator);
        map.putAll(getChangedObjects(type));
        return map;
    }

}

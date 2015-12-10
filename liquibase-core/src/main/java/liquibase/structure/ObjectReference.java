package liquibase.structure;

import liquibase.AbstractExtensibleObject;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ObjectReference extends AbstractExtensibleObject implements Comparable<ObjectReference> {

    public Class<? extends LiquibaseObject> type;
    public String name;
    public ObjectReference container;
    public boolean virtual;

    /**
     * Construct an ObjectName from the given string. If the string contains dots, it will be split into containers on the dots.
     * If null is passed, return an empty ObjectName
     */
    public static ObjectReference parse(Class<? extends LiquibaseObject> type, String string) {
        if (string == null) {
            return new ObjectReference(null);
        }

        String[] split = string.split("\\.");
        return new ObjectReference(type, split);
    }

    public ObjectReference() {
    }

    public ObjectReference(Class<? extends LiquibaseObject> type, ObjectReference container) {
        this.type = type;
        this.container = container;
    }

    public ObjectReference(Class<? extends LiquibaseObject> type, ObjectReference container, String... names) {
        this(type, container);
        if (names == null || names.length == 0) {
            this.name = null;
        } else if (names.length == 1) {
            this.container = container;
            this.name = names[0];
        } else {
            for (String name : names) {
                container = new ObjectReference(LiquibaseObject.class, container, name);
            }
            this.container = container.container;
            this.name = container.name;
        }
    }

    public ObjectReference(Class<? extends LiquibaseObject> type, String... names) {
        this(type, null, names);
    }

    public String toShortString() {
        String returnString;
        List<String> list = asList();
        if (list.size() == 0) {
            returnString = "UNNAMED";
        } else {
            returnString = StringUtils.join(list, ".", new StringUtils.StringUtilsFormatter<String>() {
                @Override
                public String toString(String obj) {
                    return StringUtils.defaultIfEmpty(obj, "UNNAMED");
                }
            });
        }
        return returnString;
    }

    @Override
    public String toString() {
        if (type == null) {
            return toShortString() + " (NO TYPE)";
        } else {
            return toShortString() + " (" + type.getSimpleName().toUpperCase() + ")";
        }
    }

    @Override
    public int compareTo(ObjectReference o) {
        if (o == null) {
            return 1;
        }
        return this.toShortString().compareTo(o.toShortString());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ObjectReference) && ((ObjectReference) obj).toShortString().equals(this.toShortString());
    }

    /**
     * If fuzzyMatching is true, ignore null place differences. Else, do a standard equals comparison
     */
    public boolean equals(ObjectReference obj, boolean fuzzyMatching) {
        if (obj == null) {
            return false;
        }
        if (fuzzyMatching) {
            if (this.name == null || obj.name == null) {
                if (this.container == null || obj.container == null) {
                    return true;
                } else {
                    return this.container.equals(obj.container, true);
                }
            } else {
                if (!this.name.equals(obj.name)) {
                    return false;
                } else {
                    if (this.container == null || obj.container == null) {
                        return true;
                    } else {
                        return this.container.equals(obj.container, true);
                    }
                }
            }
        } else {
            return this.equals(obj);
        }

    }

    @Override
    public int hashCode() {
        return toShortString().hashCode();
    }


    /**
     * Returns the {@link #asList()} result, but either null-padded out to the passed length, or truncated the the passed length
     */
    public List<String> asList(int length) {
        List<String> list = asList();
        if (length == list.size()) {
            return list;
        }
        if (length < list.size()) {
            return Collections.unmodifiableList(list.subList(list.size() - length, list.size()));
        }

        List<String> newList = new ArrayList<>(list);
        while (newList.size() < length) {
            newList.add(0, null);
        }
        return Collections.unmodifiableList(newList);
    }

    public List<String> asList() {
        if (name == null && container == null) {
            return new ArrayList<>();
        }

        List<String> returnList = new ArrayList<>();
        ObjectReference name = this;
        while (name != null) {
            returnList.add(0, name.name);
            name = name.container;
        }

        if (returnList.get(0) == null) {
            boolean sawNonNull = false;
            ListIterator<String> it = returnList.listIterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next == null && !sawNonNull) {
                    it.remove();
                } else {
                    sawNonNull = true;
                }
            }
        }

        return Collections.unmodifiableList(returnList);
    }


    /**
     * Return the number of parent containers in this ObjectName.
     * Top-level containers with a null name are not counted in the depth, but null-named containers between named containers are counted.
     */
    public int depth() {
        List<String> array = asList();
        if (array.size() == 0) {
            return 0;
        }
        return array.size() - 1;
    }

    /**
     * Returns an objectName that is truncated to the given max length
     */
    public ObjectReference truncate(int maxLength) {
        List<String> names = this.asList();
        int length = Math.min(maxLength, names.size());

        return new ObjectReference(type, names.subList(names.size() - length, names.size()).toArray(new String[length]));
    }

    public boolean instanceOf(Class<? extends LiquibaseObject> type) {
        return type.isAssignableFrom(this.type);
    }
}

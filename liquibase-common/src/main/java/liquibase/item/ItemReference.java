package liquibase.item;

import liquibase.AbstractExtensibleObject;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

import java.util.*;

/**
 * Describes how to lookup an {@link Item}. A complete reference should uniquely describe a single item for use in snapshotting or any other time you need to talk about an item.
 */
public class ItemReference<ContainerReferenceType extends ItemReference> extends AbstractExtensibleObject implements Comparable<ItemReference> {

    /**
     * The type of object this is referencing.
     */
    public Class<? extends Item> type;

    /**
     * The name of the object
     */
    public String name;

    /**
     * An ItemReference to the container/parent of the object pointed to by this reference.
     */
    public ContainerReferenceType container;

    /**
     * If true, this does not point to a "real" item, but instead something that is computed or similar.
     */
    public boolean virtual;

    public ItemReference() {
    }

    public ItemReference(Class<? extends Item> type, String name, ContainerReferenceType container) {
        this.type = type;
        this.container = container;
        this.name = name;
    }

    /**
     * Construct a reference from the given names.
     * The names array should have the top-level container at element zero and the item's name in the last element.
     * Example: "public.dbo.table_name" would be ["public","dbo","table_name"]
     *
     */
    public ItemReference(Class<? extends Item> type, String... names) {
        this.type = type;

        List<String> namesList = new ArrayList<>(Arrays.asList(CollectionUtil.createIfNull(names)));

        if (namesList.size() > 0) {
            Collections.reverse(namesList);

            Iterator<String> nameIterator = namesList.iterator();
            this.name = nameIterator.next();

            ItemReference lastReference = this;
            while (nameIterator.hasNext()) {
                String name = nameIterator.next();
                lastReference.container = lastReference.createContainer(name);
                lastReference = lastReference.container;

            }
        }
    }

    /**
     * Create the correct {@link ItemReference} implementation to use as the container for this reference.
     * Called by the multi-string constructor.
     */
    protected ContainerReferenceType createContainer(String name) {
        return (ContainerReferenceType) new ItemReference(Item.class, name);
    }

    /**
     * Returns the object's name and its container's names dot-separated. If any have a null name, calls that level "UNNAMED"
     */
    @Override
    public String toString() {
        String returnString;
        List<String> list = asList();
        if (list.size() == 0) {
            returnString = "UNNAMED";
        } else {
            returnString = StringUtil.join(list, ".", new StringUtil.StringUtilFormatter<String>() {
                @Override
                public String toString(String obj) {
                    return StringUtil.defaultIfEmpty(obj, "UNNAMED");
                }
            });
        }
        return returnString;
    }

    @Override
    public int compareTo(ItemReference o) {
        if (o == null) {
            return 1;
        }

        int compareTo = ObjectUtil.defaultIfNull(this.type, Item.class).getClass().getName().compareTo(ObjectUtil.defaultIfNull(o.type, Item.class).getClass().getName());

        if (compareTo != 0) {
            return compareTo;
        }

        return this.toString().compareTo(o.toString());
    }

    /**
     * Uses {@link #type} and {@link #toString()} for equality.
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null
                && (obj instanceof ItemReference)
                && ObjectUtil.defaultIfNull(this.type, Item.class).equals(ObjectUtil.defaultIfNull(((ItemReference) obj).type, Item.class))
                && obj.toString().equals(this.toString());
    }

    /**
     * If fuzzyMatching is true, ignore null place differences. Else, do a standard equals comparison
     */
    public boolean equals(ItemReference obj, boolean fuzzyMatching) {
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
        return toString().hashCode();
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

    /**
     * Flattens this reference's name and the names of its containers up the chain into a list.
     * Will cut off the list once it gets to containers with null names the rest of the way up the chain.
     * For example, "name1.null.name3" will output [name1,null,name3] but "null.name2.name3" will return just [name2,name3]
     */
    public List<String> asList() {
        if (name == null && container == null) {
            return new ArrayList<>();
        }

        List<String> returnList = new ArrayList<>();
        ItemReference ref = this;
        while (ref != null) {
            returnList.add(0, ref.name);
            ref = ref.container;
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
     * Flattens this object and its parent container tree into a list.
     */
    public List<ItemReference> asReferenceList() {
        if (name == null && container == null) {
            return new ArrayList<>();
        }

        List<ItemReference> returnList = new ArrayList<>();
        ItemReference ref = this;
        while (ref != null) {
            returnList.add(0, ref);
            ref = ref.container;
        }

        if (returnList.get(0) == null) {
            boolean sawNonNull = false;
            ListIterator<ItemReference> it = returnList.listIterator();
            while (it.hasNext()) {
                ItemReference next = it.next();
                if ((next == null || next.name == null) && !sawNonNull) {
                    it.remove();
                } else {
                    sawNonNull = true;
                }
            }
        }

        return Collections.unmodifiableList(returnList);
    }

    /**
     * Returns a new ItemReference that is truncated to the given max length.
     * If maxLength is greater than what is in this reference, return a clone of this object.
     */
    public ItemReference truncate(int maxLength) {
        int size = this.asList().size();

        if (maxLength >= size) {
            return (ItemReference) this.clone();
        }

        ItemReference returnObject = (ItemReference) this.clone();
        ItemReference refToCutOff = returnObject;
        for (int i = 0; i < maxLength - 1; i++) {
            refToCutOff.container = (ItemReference) refToCutOff.container.clone();
            refToCutOff = refToCutOff.container;
        }

        refToCutOff.container = null;
        return returnObject;
    }

    /**
     * Return true if the type of this reference is an instance of the given type.
     * If this reference's type or the given type is null, assume "Item" as the type.
     */
    public boolean instanceOf(Class<? extends Item> type) {
        Class<? extends Item> compareType = this.type;
        if (compareType == null) {
            compareType = Item.class;
        }
        if (type == null) {
            return compareType.equals(Item.class);
        }

        return type.isAssignableFrom(compareType);
    }
}

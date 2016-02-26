package liquibase.item;

import liquibase.AbstractExtensibleObject;

/**
 * A convenience base class for {@link Item} implementations.
 * For database objects, extend from {@link DatabaseObject}.
 */
public abstract class AbstractItem<ReferenceType extends ItemReference> extends AbstractExtensibleObject implements Item<ReferenceType> {

    public String name;

    public AbstractItem() {
    }

    public AbstractItem(String name) {
        this.name = name;
    }

    /**
     * Returns the name. Marked final so subclasses don't change business logic and make it not match get("name")
     */
    public final String getName() {
        return name;
    }

    /**
     * Default implementation compares the {@link #toReference()} output
     */
    @Override
    public int compareTo(Object o) {
        if (o == null || !(o instanceof Item)) {
            return 1;
        }
        return this.toReference().compareTo(((Item) o).toReference());
    }

    @Override
    public int hashCode() {
        return toReference().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Item
                && this.toReference().equals(((Item) obj).toReference());
    }
}

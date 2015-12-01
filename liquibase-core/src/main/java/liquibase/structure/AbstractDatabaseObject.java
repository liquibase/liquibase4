package liquibase.structure;

import liquibase.AbstractExtensibleObject;
import liquibase.util.StringUtils;

public abstract class AbstractDatabaseObject extends AbstractExtensibleObject implements DatabaseObject {

    public String snapshotId;
    public ObjectReference container;
    public String name;

    @Override
    public String getObjectTypeName() {
        return StringUtils.lowerCaseFirst(getClass().getSimpleName());
    }

    public AbstractDatabaseObject() {
    }

    public AbstractDatabaseObject(String name) {
        this.name = name;
    }

    public AbstractDatabaseObject(ObjectReference nameAndContainer) {
        this.name = nameAndContainer.name;
        this.container = nameAndContainer.container;
    }

    public AbstractDatabaseObject(ObjectReference container, String name) {
        this.container = container;
        this.name = name;
    }



    /**
     * Returns the name. Marked final so subclasses don't change business logic and make it not match get("name")
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the schema. Marked final so subclasses don't change business logic and make it not match get("schema")
     */
    @Override
    public final ObjectReference getContainer() {
        return container;
    }

    /**
     * Returns the snapshotId. Marked final so subclasses don't change business logic and make it not match get("snapshotId")
     */
    @Override
    public final String getSnapshotId() {
        return snapshotId;
    }

    @Override
    public boolean snapshotByDefault() {
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || !(o instanceof DatabaseObject)) {
            return 1;
        }
        return this.toReference().compareTo(((DatabaseObject) o).toReference());
    }


    /**
     * Convenience method for subclasses to use in the standard toString(). Returns name, prefixed with container if it exists
     */
    protected String toString(ObjectReference container, String name) {
        String string = name;
        if (container != null) {
            string = container.toString()+"."+name;
        }
        return string;
    }

    @Override
    public String toString() {
        return toReference().toString();
    }

    @Override
    public int hashCode() {
        return toReference().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DatabaseObject
                && this.toReference().equals(((DatabaseObject) obj).toReference());
    }

    @Override
    public ObjectReference toReference() {
        return new ObjectReference(getClass(), container, name);
    }
}

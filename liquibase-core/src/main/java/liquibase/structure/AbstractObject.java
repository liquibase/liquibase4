package liquibase.structure;

import liquibase.AbstractExtensibleObject;
import liquibase.util.StringUtils;

public abstract class AbstractObject extends AbstractExtensibleObject implements LiquibaseObject {

    public String snapshotId;
    public String name;

    @Override
    public String getObjectTypeName() {
        return StringUtils.lowerCaseFirst(getClass().getSimpleName());
    }

    public AbstractObject() {
    }

    public AbstractObject(String name) {
        this.name = name;
    }

    /**
     * Returns the name. Marked final so subclasses don't change business logic and make it not match get("name")
     */
    public final String getName() {
        return name;
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
        if (o == null || !(o instanceof LiquibaseObject)) {
            return 1;
        }
        return this.toReference().compareTo(((LiquibaseObject) o).toReference());
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
    public int hashCode() {
        return toReference().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LiquibaseObject
                && this.toReference().equals(((LiquibaseObject) obj).toReference());
    }

    @Override
    public ObjectReference toReference() {
        return new ObjectReference(getClass(), name);
    }
}

package liquibase.item;

/**
 * Base class for all {@link ItemReference}s that point to {@link DatabaseObject}'s
 * @param <ContainerReferenceType>
 */
public abstract class DatabaseObjectReference<ContainerReferenceType extends ItemReference> extends ItemReference<ContainerReferenceType> {

    public DatabaseObjectReference() {
    }

    public DatabaseObjectReference(Class<? extends DatabaseObject> type, String name, ContainerReferenceType container) {
        super(type, name, container);
    }

    public DatabaseObjectReference(Class<? extends DatabaseObject> type, String... names) {
        super(type, names);
    }
}

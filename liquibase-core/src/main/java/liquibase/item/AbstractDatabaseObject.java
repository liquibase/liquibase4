package liquibase.item;

/**
 * A convenience base class for {@link DatabaseObject} implementations.
 */
public abstract class AbstractDatabaseObject<ReferenceType extends DatabaseObjectReference> extends AbstractItem<ReferenceType> implements DatabaseObject<ReferenceType> {

    public AbstractDatabaseObject() {
    }

    public AbstractDatabaseObject(String name) {
        super(name);
    }
}

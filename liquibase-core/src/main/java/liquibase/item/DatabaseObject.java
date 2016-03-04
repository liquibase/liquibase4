package liquibase.item;

/**
 * Common interface for Database-related {@link Item}s
 *
 * @see AbstractDatabaseObject for convenience base class.
 */
public interface DatabaseObject<ReferenceType extends DatabaseObjectReference> extends Item<ReferenceType> {

    /**
     * The remarks stored with this object.
     */
    String getRemarks();
}

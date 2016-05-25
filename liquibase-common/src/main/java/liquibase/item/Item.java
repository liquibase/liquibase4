package liquibase.item;

import liquibase.ExtensibleObject;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

/**
 * Describes something that can be managed by Liquibase. Used by {@link liquibase.snapshot.Snapshot} to describe what was found during the snapshot process, and by {@link liquibase.action.Action} classes to describe objects that should be created.
 * <br><br>
 * Currently Liquibase only deals with database objects, but this base interface is purposefully more generic so we can snapshot and manage other types of objects down the road, like Server, OSUser, ActiveDirectoryGroup, etc.
 *
 * @see DatabaseObject for base interface for all database-related items.
 */
public interface Item<ReferenceType extends ItemReference> extends Comparable, ExtensibleObject {

    /**
     * Returns the name of the object.
     */
    String getName();

    /**
     * Creates a reference to this item. The reference can be used to uniquely identify this object in snapshots etc.
     */
    ReferenceType toReference();

    /**
     * Creates a custom {@link ParsedNodeUnprocessor} instance to setup in the {@link liquibase.parser.unprocessor.ParsedNodeUnprocessorFactory}.
     * Because many items need to do custom unprocessing, we use this method instead of needing to add these classes to the META-INF/services configuration.
     * Can return null if no unprocessors are needed.
     */
    ParsedNodeUnprocessor createUnprocessor();
}


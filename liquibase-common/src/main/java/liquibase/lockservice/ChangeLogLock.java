package liquibase.lockservice;

import liquibase.AbstractExtensibleObject;

import java.util.Date;

/**
 * Information about the database changelog lock which allows only one instance of Liquibase to attempt to
 * update a database at a time.
 *
 * Standard fields are immutable
 */
public class ChangeLogLock extends AbstractExtensibleObject {

    /**
     * True if the current instance is the owner of the lock
     */
    public final boolean isOwner;

    /**
     * The ID of the lock. Currently only id of 1 is used, but is set up for future use and/or extensions.
     */
    public final int id;

    /**
     * Date lock was granted.
     */
    public final Date lockGranted;

    /**
     * Current lock owner.
     */
    public final String lockedBy;

    public ChangeLogLock(int id, Date lockGranted, String lockedBy, boolean isOwner) {
        this.id = id;
        this.lockGranted = new Date(lockGranted.getTime());
        this.lockedBy = lockedBy;
        this.isOwner = isOwner;
    }
}

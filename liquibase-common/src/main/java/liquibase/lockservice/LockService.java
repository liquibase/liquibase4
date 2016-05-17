package liquibase.lockservice;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.plugin.Plugin;

/**
 * LockService implementations ensure that only one instance of Liquibase runs against a given environment at one time.
 *
 * @see AbstractLockService
 * @see StandardLockService
 */
public interface LockService extends Plugin, ExtensibleObject {

    /**
     * Returns the priority of this instance for the given scope.
     */
    int getPriority(Scope scope);

    /**
     * Performs any initialization for this lock service to work.
     */
    void init(Scope scope) throws LiquibaseException;

    /**
     * Attempts to get a lock. Continues to wait until it is able to get a lock.
     * If we are already the lock owner, return right away.
     * Implementations may chose to give up after a while and throw a LockException.
     *
     * @see #acquireLock(Scope) for getting a lock without waiting.
     */
    void waitForLock(Scope scope) throws LockException;


    /**
     * Attempts to get a lock.
     * If we are already the lock owner, return that we own it.
     * If someone else currently owns the lock, return who owns it.
     *
     * @see #waitForLock(Scope) to block until we are able to get the lock.
     */
    ChangeLogLock acquireLock(Scope scope) throws LockException;

    /**
     * Releases our lock.
     *
     * @throws LockException if we don't own the lock or the lock cannot be released.
     */
    void releaseLock(Scope scope) throws LockException;

    /**
     * Releases whatever locks there are, even if they are not ours.
     *
     * @throws LockException if the lock cannot be released.
     */
    void forceReleaseLock(Scope scope) throws LockException;

}

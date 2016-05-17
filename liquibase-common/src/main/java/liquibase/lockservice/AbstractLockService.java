package liquibase.lockservice;

import liquibase.AbstractExtensibleObject;
import liquibase.ExecuteMode;
import liquibase.Scope;
import liquibase.action.core.CommentAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.exception.LockException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.NetUtil;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;

/**
 * Convenience base class for {@link LockService} implementations.
 */
public abstract class AbstractLockService extends AbstractExtensibleObject implements LockService {

    /**
     * Local hostname to use when checking and creating locks
     */
    public static final String hostname;

    /**
     * Local host address to use when checking and creating locks
     */
    public static final String hostaddress;

    /**
     * Description of host to use when checking and creating locks.
     * Useful when there are multiple instances of liquibase coming from the same host.
     */
    public static final String hostDescription = System.getProperty("liquibase.hostDescription") == null ? "" : "#" + System.getProperty("liquibase.hostDescription");

    /**
     * String to use in the lockowner field when creating a lock and checking if we are the owner.
     */
    public String myDescription;

    /**
     * Number of seconds to wait before re-checking lock in {@link #waitForLock(Scope)}
     */
    public Long pollRate = 30L;

    /**
     * Total number of seconds to wait before giving up in {@link #waitForLock(Scope)}
     */
    public Long waitTime = 5 * 60L;


    static {
        try {
            hostname = NetUtil.getLocalHostName();
            hostaddress = NetUtil.getLocalHostAddress();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public AbstractLockService() {
        this.myDescription = hostname + hostDescription + " (" + hostaddress + ")";
    }

    /**
     * Returns information about the current lock.
     * Returns null if there is no current lock.
     */
    protected abstract ChangeLogLock getCurrentLock(Scope scope) throws LockException;

    /**
     * Contains the logic to create the lock.

     * @throws LockException if the lock cannot be created.
     */
    protected abstract ChangeLogLock doLock(Scope scope) throws LockException;


    /**
     * Default implementation calls {@link #acquireLock(Scope)} until {@link #waitTime} is up or we get the lock.
     */
    @Override
    public void waitForLock(Scope scope) throws LockException {

        ChangeLogLock lock = null;
        long timeToGiveUp = new Date().getTime() + (this.waitTime * 1000);
        while ((lock == null || !lock.isOwner) && new Date().getTime() < timeToGiveUp) {
            lock = acquireLock(scope);
            if (!lock.isOwner) {
                LoggerFactory.getLogger(getClass()).info("Waiting for changelog lock....");
                try {
                    Thread.sleep(this.pollRate * 1000);
                } catch (InterruptedException e) {
                    ;
                }
            }
        }

        if (lock == null) {
            throw new LockException("Could not determine lock owner");
        }

        if (!lock.isOwner) {
            String lockedBy;
            lockedBy = lock.lockedBy + " since " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(lock.lockGranted);
            throw new LockException("Could not acquire change log lock.  Currently locked by " + lockedBy);
        }
    }

    /**
     * Default implementation checks the current owner and calls {@link #forceReleaseLock(Scope)} if we are the owner.
     */
    @Override
    public void releaseLock(Scope scope) throws LockException {
        ChangeLogLock currentLock = getCurrentLock(scope);
        if (currentLock.isOwner) {
            forceReleaseLock(scope);
        } else {
            throw new LockException("Cannot release lock, not lock owner. Current owner is '"+currentLock.lockedBy+"'");
        }
    }

    /**
     * Default implementation checks the current owner with {@link #getCurrentLock(Scope)} and calls {@link #doLock(Scope)} if we are not the owner.
     */
    @Override
    public ChangeLogLock acquireLock(Scope scope) throws LockException {
        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);

        try {
            ChangeLogLock currentLock = getCurrentLock(scope);
            if (scope.getExecuteMode() == ExecuteMode.READ_ONLY || currentLock == null || !currentLock.isOwner) {
                executor.execute(new CommentAction("Lock Liquibase"), scope);
                return doLock(scope);
            } else {
                return currentLock;
            }
        } catch (Exception e) {
            throw new LockException(e);
        }
    }

}

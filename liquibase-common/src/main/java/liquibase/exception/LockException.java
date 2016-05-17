package liquibase.exception;

/**
 * Exception thrown by {@link liquibase.lockservice.LockService}
 */
public class LockException extends LiquibaseException {

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }
}

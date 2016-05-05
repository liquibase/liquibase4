package liquibase.exception;

/**
 * Exception thrown when interacting directly with a database.
 */
public class DatabaseException extends LiquibaseException {

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}

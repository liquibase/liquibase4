package liquibase.exception;

/**
 * Base class for all Liquibase exceptions.
 */
public class LiquibaseException extends Exception{

    public LiquibaseException() {
    }

    public LiquibaseException(String message) {
        super(message);
    }

    public LiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LiquibaseException(Throwable cause) {
        super(cause);
    }
}

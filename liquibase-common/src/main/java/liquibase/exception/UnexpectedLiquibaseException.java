package liquibase.exception;

/**
 * Unchecked exception to be used when something unexpected happens that doesn't fit into other {@see LiquibaseException} exceptions.
 */
public class UnexpectedLiquibaseException extends RuntimeException {

    public UnexpectedLiquibaseException(String message) {
        super(message);
    }

    public UnexpectedLiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedLiquibaseException(Throwable cause) {
        super(cause);
    }
}

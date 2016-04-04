package liquibase.exception;

/**
 * Thrown when {@link liquibase.DependencyObject}s cannot be sorted.
 */
public class DependencyException extends LiquibaseException{

    public DependencyException(String message) {
        super(message);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyException(Throwable cause) {
        super(cause);
    }
}

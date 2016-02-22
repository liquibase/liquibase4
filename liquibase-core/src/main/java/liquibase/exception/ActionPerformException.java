package liquibase.exception;

/**
 * Exception thrown when executing a {@link liquibase.action.Action}
 */
public class ActionPerformException extends LiquibaseException {

    public ActionPerformException() {
    }

    public ActionPerformException(String message) {
        super(message);
    }

    public ActionPerformException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionPerformException(Throwable cause) {
        super(cause);
    }
}

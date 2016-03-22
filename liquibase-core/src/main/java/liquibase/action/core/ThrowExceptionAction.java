package liquibase.action.core;

import liquibase.action.AbstractAction;

/**
 * Throws an exception when executed.
 */
public class ThrowExceptionAction extends AbstractAction {

    public Throwable exception;

    public ThrowExceptionAction() {
    }

    /**
     * Creates a {@link liquibase.action.core.ThrowExceptionAction.ThrowExceptionActionException} with the given message.
     */
    public ThrowExceptionAction(String message) {
        this.exception = new ThrowExceptionActionException(message);
    }

    public ThrowExceptionAction(Throwable exception) {
        this.exception = exception;
    }

    public static class ThrowExceptionActionException extends RuntimeException {

        public ThrowExceptionActionException() {
        }

        public ThrowExceptionActionException(String message) {
            super(message);
        }

        public ThrowExceptionActionException(String message, Throwable cause) {
            super(message, cause);
        }

        public ThrowExceptionActionException(Throwable cause) {
            super(cause);
        }
    }
}

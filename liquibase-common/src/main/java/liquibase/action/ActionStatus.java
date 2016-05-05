package liquibase.action;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.item.ItemReference;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

import java.util.*;

/**
 * Returned by {@link liquibase.actionlogic.ActionLogic#checkStatus(Action, Scope)} to describe if the action has already been applied.
 * Multiple messages can be included in this status response, but {@link #getStatus()} will roll them up into a single {@link liquibase.action.ActionStatus.Status} result.
 * <br><br>
 * There are multiple "assert" methods such as {@link #assertCorrect(boolean, String)} and {@link #assertCorrect(Object, Object, String)}. This object tracks if any of them have been
 * called and will return {@link liquibase.action.ActionStatus.Status#unknown} if none have been called.
 *
 * @see liquibase.action.ActionStatus.Status for possible states.
 */
public class ActionStatus {

    protected Throwable exception;

    private Map<Status, SortedSet<String>> messages = new HashMap<>();
    private boolean atLeastOneAssertion = false;

    public ActionStatus() {
        messages.put(Status.unknown, new TreeSet<String>());
        messages.put(Status.incorrect, new TreeSet<String>());
        messages.put(Status.notApplied, new TreeSet<String>());
    }

    /**
     * Add the given message under the given Status. Message defaults to "No message" if null.
     */
    public ActionStatus add(Status status, String message) {
        messages.get(status).add(ObjectUtil.defaultIfNull(message, "No message"));

        return this;
    }

    /*
     * Add the given exception as an {@link liquibase.action.ActionStatus.Status#unknown} message;
     */
    public ActionStatus unknown(Throwable exception) {
        this.exception = exception;
        return add(Status.unknown, exception.getMessage() + " (" + exception.getClass().getName() + ")");
    }

    /*
     * Add the given exception as an {@link liquibase.action.ActionStatus.Status#unknown} message;
     */
    public ActionStatus unknown(String message) {
        return add(Status.unknown, message);
    }

    /**
     * Convience method to call {@link #add(Status, String)} using {@link Status#notApplied} if the applied flag is false.
     */
    public ActionStatus assertApplied(boolean applied, String notAppliedMessage) {
        if (!applied) {
            add(Status.notApplied, notAppliedMessage);
        }
        atLeastOneAssertion = true;
        return this;
    }

    /**
     * Convience method to call {@link #add(Status, String)} using {@link Status#incorrect} if the applied flag is false.
     */
    public ActionStatus assertCorrect(boolean correct, String incorrectMessage) {
        if (!correct) {
            add(Status.incorrect, incorrectMessage);
        }

        atLeastOneAssertion = true;
        return this;
    }


    /**
     * Calls {@link #assertCorrect(Object, Object, String)} but with a standard message
     */
    public <T extends ExtensibleObject> ActionStatus assertCorrect(T correctObject, T objectToCheck) {
        return assertCorrect(correctObject, objectToCheck, (String) null);
    }

    /**
     * Compares two objects and if they are different, adds the given message under the {@link @Satatus#incorrect} status.
     * <p/>
     * If the passed objects are {@link ExtensibleObject}s, calls {@link #assertCorrect(ExtensibleObject, ExtensibleObject, Collection)} excluding no properties.
     */
    public <T> ActionStatus assertCorrect(T correctObject, T objectToCheck, String invalidMessage) {
        if (invalidMessage != null) {
            invalidMessage = invalidMessage + ": ";
        }
        if (correctObject instanceof ExtensibleObject) {
            return assertCorrect((ExtensibleObject) correctObject, (ExtensibleObject) objectToCheck, (Collection<String>) null);
        } else {
            if (correctObject == null && objectToCheck == null) {
                return this;
            } else if (correctObject == null || objectToCheck == null) {
                return assertCorrect(false, invalidMessage + "expected " + correctObject + " but got " + objectToCheck);
            } else {
                return assertCorrect(correctObject.equals(objectToCheck), invalidMessage + "expected " + correctObject + " but got " + objectToCheck);
            }
        }
    }

    /**
     * Compares two objects and if any of the non-excludedFields are different, adds an {@link Status#incorrect} message.
     */
    public <T extends ExtensibleObject> ActionStatus assertCorrect(T correctObject, T objectToCheck, Collection<String> excludeFields) {
        if (excludeFields == null) {
            excludeFields = new HashSet<>();
        }

        for (String property : correctObject.getAttributes()) {
            if (!excludeFields.contains(property)) {
                assertPropertyCorrect(correctObject, objectToCheck, property);
            }
        }

        return this;

    }

    /**
     * Compares the given property in to ExtensibleObjects.
     * Does not support and therefore does not check Collection values.
     * For {@link ItemReference} does a "fuzzy" equals.
     */
    public ActionStatus assertPropertyCorrect(ExtensibleObject correctObject, ExtensibleObject objectToCheck, String propertyName) {
        Object correctValue = correctObject.get(propertyName, Object.class);
        Object checkValue = objectToCheck.get(propertyName, Object.class);

        if (correctValue instanceof Collection) {
            return this;
        }

        boolean correct;
        if (correctValue == null) {
            correct = checkValue == null;
        } else {
            if (correctValue instanceof ItemReference) {
                correct = ((ItemReference) correctValue).equals((ItemReference) checkValue, true);
            } else {
                correct = correctValue.equals(checkValue);

                if (!correct && checkValue != null) { //fall back to string comparison in case there are slight datatype differences
                    correct = correctValue.toString().equals(checkValue.toString());
                }
            }
        }
        return assertCorrect(correct, "'" + propertyName + "' is incorrect on " + objectToCheck.describe() + " (expected '" + correctValue + "' got '" + checkValue + "')");
    }

    /**
     * Returns the {@link liquibase.action.ActionStatus.Status} enum value based on what has been set on this object.
     * The priority order for a response is:
     * <ol>
     * <li>Nothing previously set: return Unknown</li>
     * <li>Unknown message(s)</li>
     * <li>Not applied message(s)</li>
     * <li>Incorrect message(s)</li>
     * <li>Complete</li>
     * </ol>
     */
    public Status getStatus() {
        if (messages.get(Status.unknown).size() > 0) {
            return Status.unknown;
        } else if (messages.get(Status.notApplied).size() > 0) {
            return Status.notApplied;
        } else if (messages.get(Status.incorrect).size() > 0) {
            return Status.incorrect;
        } else {
            if (atLeastOneAssertion) {
                return Status.applied;
            } else {
                return Status.unknown;
            }
        }
    }

    /**
     * Return messages for the status returned by {@link #getStatus()}. If more than one message, they are returned comma separated.
     * Returns null if there are no messages.
     */
    public String getMessage() {
        Status status = getStatus();
        if (status == Status.applied) {
            return null;
        } else {
            SortedSet<String> statusMessages = messages.get(status);
            if (statusMessages.size() == 0) {
                return null;
            } else {
                return StringUtil.join(statusMessages, ", ");
            }
        }
    }

    /**
     * Convenience method to check that the status is {@link Status#applied}
     */
    public boolean isApplied() {
        return getStatus() == Status.applied;
    }

    /**
     * Return the last exception set using {@link #unknown(Throwable)}
     */
    public Throwable getException() {
        return exception;
    }

    @Override
    public String toString() {
        String out = getStatus().name;

        String message = getMessage();
        if (out.equals("Unknown") && message == null && !atLeastOneAssertion) {
            message = "no assertions made";
        }

        if (message != null) {
            out += ": " + message;
        }

        return out;
    }

    /**
     * Adds the given statuses to this object. Overwrites the exception if any of the passed statuses contains one.
     */
    public void addAll(ActionStatus status) {
        for (Map.Entry<ActionStatus.Status, SortedSet<String>> entry : status.messages.entrySet()) {
            this.messages.get(entry.getKey()).addAll(entry.getValue());
        }

        if (status.atLeastOneAssertion) {
            this.atLeastOneAssertion = true;
        }
        if (status.exception != null) {
            this.exception = status.exception;
        }

    }

    /**
     * Mark status as OK when there is nothing to check.
     */
    public ActionStatus nothingToCheck() {
        return assertApplied(true, "nothing to check");
    }

    /**
     * Enumeration of possible {@link ActionStatus} states
     */
    public enum Status {
        /**
         * The action has been fully completed
         */
        applied("Applied"),

        /**
         * The action was executed, but the current state doesn't quite match. For example, a CreateTableAction was ran, but some of the column definitions don't match
         */
        incorrect("Incorrect"),

        /**
         * The action was not applied
         */
        notApplied("Not Applied"),

        /*
        The current state cannot be checked.
        Return this if there was an exception checking or, an unexpected state is encountered, or if you cannot access needed environments.
         */
        unknown("Unknown");


        private String name;

        Status(String name) {
            this.name = name;
        }
    }
}

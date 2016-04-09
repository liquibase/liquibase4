package liquibase.changelog.filter;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;

/**
 * Implementations control what {@link ChangeSet}s are visited by {@link liquibase.changelog.ChangeLogIterator}.
 */
public interface ChangeSetFilter {

    /**
     * Returns whether the changeSet should be allowed and why or why not.
     */
    Result allow(ChangeSet changeSet, Scope scope);


    /**
     * Describes whether a changeSet should be filtered and why in {@link liquibase.changelog.ChangeLogIterator}
     */
    class Result extends AbstractExtensibleObject {

        /**
         * True if the changeSet should be visited. False if it should not be.
         */
        public final boolean allow;

        /**
         * Why the changeSet was filtered or not.
         */
        public final String message;

        public Result(boolean allow, String message) {
            this.allow = allow;
            this.message = message;
        }


        @Override
        public String toString() {
            if (allow) {
                return "Will run because " + message;
            } else {
                return "Will NOT run because " + message;
            }
        }
    }
}

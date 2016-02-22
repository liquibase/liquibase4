package liquibase.database;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.plugin.Plugin;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

import java.util.Date;

/**
 * Implementations of this interface describe the idiosyncrasies of a particular database.
 * Instances of this interface can contain connections to online and offline databases.
 * Normally consider extending {@link AbstractJdbcDatabase} rather than this interface directly.
 * Implements {@link Plugin} so that extensions can override standard implementations if needed.
 */
@SuppressWarnings("unused")
public interface Database extends Plugin, ExtensibleObject {

    int getPriority(Scope scope);

    /**
     * Returns an all-lower-case short name of this database.  Used for end-user selecting of database type such as the DBMS precondition.
     * Database implementations are grouped by this value then sorted by {@link #getPriority(Scope)}
     */
    String getShortName();

    /**
     * Return true if this subclass supports the given connection.
     */
    boolean supports(DatabaseConnection conn, Scope scope);

    /**
     * Return the current connection on this Database, or null if not set.
     */
    DatabaseConnection getConnection();

    /**
     * Sets the current connection for this Database. This implementation may make configurations changes to the connection in this method if needed.
     */
    void setConnection(DatabaseConnection conn, Scope scope);

    /**
     * Return true if the database supports the given feature.
     * This method and {@link #supports(String, Scope)} must return the same value for featureKeys that match a {@link liquibase.database.Database.Feature}.
     */
    boolean supports(Database.Feature feature, Scope scope);

    /**
     * Return true if the database supports the given feature.
     * This method and {@link #supports(liquibase.database.Database.Feature, Scope)} must return the same value for featureKeys that match a {@link liquibase.database.Database.Feature}.
     */
    boolean supports(String featureKey, Scope scope);

    /**
     * Returns true if the database supports the given object type
     */
    boolean supports(Class<? extends LiquibaseObject> type, Scope scope);

    /**
     * Returns database-specific function for generating the current date/time.
     */
    String getCurrentDateTimeFunction(Scope scope);

    /**
     * Returns string used as line comment prefix
     */
    String getLineComment(Scope scope);

    /**
     * Return true if the given object is a system object. System objects should not be included in snapshots
     */
    boolean isSystemObject(ObjectReference object, Scope scope);

    /**
     * Return true if the given object is used by liquibase. Examples include the databasechangelog and databasechangeloglock tables.
     */
    boolean isLiquibaseObject(ObjectReference object, Scope scope);

    /**
     * Return true if the given string is a reserved word
     */
    boolean isReservedWord(String string, Scope scope);

    /**
     * Return the given date as a string containing just the date to be passed to the database. String should be quoted as needed.
     */
    String getDateString(Date date, Scope scope);

    /**
     * Return the given date as a string containing just the time to be passed to the database. String should be quoted as needed.
     */
    String getTimeString(Date time, Scope scope);

    /**
     * Return the given date as a string containing the date and time to be passed to the database.  String should be quoted as needed.
     */
    String getDateTimeString(Date timeStamp, Scope scope);

    /**
     * Quote the given object name.
     * The name is assumed to be a simple object name, it is not parsed or checked for existing quoting or schema separators etc.
     * Method should check {@link liquibase.Scope.Attr#quotingStrategy} to determine how to quote the object.
     */
    String quoteObjectName(String objectName, Class<? extends LiquibaseObject> objectType, Scope scope);

    /**
     * Quote the given object reference.
     * Normally include all container levels quoted as well, unless the database in scope doesn't support fully qualified names for that object type.
     * Method should check {@link liquibase.Scope.Attr#quotingStrategy} to determine how to quote the object.
     */
    String quoteObjectName(ObjectReference objectReference, Scope scope);

    /**
     * Return the given string quoted, with any special characters within the string handled.
     */
    String quoteString(String string, Scope scope);

    /**
     * Return how identifiers are handled, based on the passed quoting strategy.
     */
    IdentifierCaseHandling getIdentifierCaseHandling(Class<? extends LiquibaseObject> type, boolean quoted, Scope scope);

    /**
     * Return true if the given type's name is a valid object name format.
     * The object's case is not taken into account with this check, that is handled with {@link #getIdentifierCaseHandling(Class, boolean, Scope)}.
     * There is no checking if the object exists, only if it is valid as a name.
     */
    boolean isValidObjectName(String name, boolean quoted, Class<? extends LiquibaseObject> type, Scope scope);

    /**
     * Enum containing standard features used in {@link #supports(Feature, Scope)}
     */
    enum Feature {

        DDL_IN_TRANSACTION(true, "True if the database supports DDL within a transaction. This doesn't specify whether the database auto-commits DDL statements, just whether an error will be thrown if there is an active transaction when a DDL statement is executed"),
        DEFERRABLE_CONSTRAINTS(false, "True if the database supports deferrable constraints"),
        AUTO_INCREMENT(true, "True if the database supports auto-increment/identity columns"),
        CLUSTERED_INDEXES(false, "True if the database supports creating clustered indexes, regardless of what they call them."),
        DISABLING_FOREIGN_KEYS(false, "True if foreign keys can be temporarily disabled"),
        TABLESPACES(false, "True if tablespaces can be defined"),
        NAMED_PRIMARY_KEYS(true, "True if primary keys can have unique names"),
        INDEXES_DESC(true, "True if indexes can be defined as descending"),
        INDEXES_ASC(true, "True if indexes can be defined as ascending"),
        AUTO_CREATES_INDEXES_FOR_FOREIGN_KEYS(false, "True if indexes are automatically created when a foreign key is created")
        ;

        private boolean supportedByDefault;
        private String description;

        Feature(boolean supportedByDefault, String description) {
            this.supportedByDefault = supportedByDefault;
            this.description = description;
        }

        public boolean getSupportedByDefault() {
            return supportedByDefault;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Enum used by {@link #getIdentifierCaseHandling(Class, boolean, Scope)}
     */
    enum IdentifierCaseHandling {

        UPPERCASE,
        LOWERCASE,
        CASE_SENSITIVE,

    }

    /**
     * Strategy regards quoting object names e.g. table, column, index names etc.
     */
    enum QuotingStrategy {
        /**
         * Every object gets quoted. E.g. person becomes "person"
         */
        QUOTE_ALL_OBJECTS,

        /**
         * Only quote objects that need to be quoted, such as reserved words and names with special characters.
         */
        QUOTE_ONLY_REQUIRED

    }
}


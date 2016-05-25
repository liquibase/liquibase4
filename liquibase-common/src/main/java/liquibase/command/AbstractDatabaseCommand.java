package liquibase.command;

import liquibase.ExtensibleObjectAttribute;
import liquibase.Scope;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;

/**
 * Base class for {@link LiquibaseCommand} implementations that deal with a database.
 *
 * @see #setupDatabase(Scope) for getting the database object
 */
public abstract class AbstractDatabaseCommand<T extends CommandResult> extends AbstractLiquibaseCommand<T> {

    @ExtensibleObjectAttribute(description = "Database url", required = true)
    public String url;

    @ExtensibleObjectAttribute(description = "Database username")
    public String username;

    @ExtensibleObjectAttribute(description = "Database password")
    public String password;

    @ExtensibleObjectAttribute(description = "Database driver")
    public String driver;

    @ExtensibleObjectAttribute(description = "Liquibase Database class to use")
    public String databaseClass;

    /**
     * Returns a scope with with the database attribute set.
     *
     * If the passed scope already has a database, returns the same scope object. Otherwise, creates a new database object from the paramaters in this command.
     */
    protected Scope setupDatabase(Scope scope) throws LiquibaseException {
        if (scope.getDatabase() == null) {
            scope = scope.child(Scope.Attr.database, scope.getSingleton(DatabaseFactory.class).connect(
                    url,
                    username,
                    password,
                    driver,
                    databaseClass,
                    null, //todo
                    null, //todo
                    scope));
        }
        return scope;
    }


}

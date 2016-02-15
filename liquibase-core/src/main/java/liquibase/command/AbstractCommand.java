package liquibase.command;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;

/**
 * Base class for {@link LiquibaseCommand} implementations
 */
public abstract class AbstractCommand<T extends CommandResult> extends AbstractExtensibleObject implements LiquibaseCommand<T> {

    /**
     * Calls validate method and then {@link #run(Scope)}
     */
    public final T execute(Scope scope) throws CommandExecutionException {
        this.validate(scope);
        try {
            return this.run(scope);
        } catch (Exception e) {
            if (e instanceof CommandExecutionException) {
                throw (CommandExecutionException) e;
            } else {
                throw new CommandExecutionException(e);
            }
        }
    }

    /**
     * Implement command logic in this method. Validation will have been performed before this is reached.
     */
    protected abstract T run(Scope scope) throws Exception;
}

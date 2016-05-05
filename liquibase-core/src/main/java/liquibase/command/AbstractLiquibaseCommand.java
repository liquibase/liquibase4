package liquibase.command;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.CommandExecutionException;

import java.util.Objects;

/**
 * Base class for {@link LiquibaseCommand} implementations
 */
public abstract class AbstractLiquibaseCommand<T extends CommandResult> extends AbstractExtensibleObject implements LiquibaseCommand<T> {

    @Override
    public int getPriority(String commandName, Scope scope) {
        if (Objects.equals(commandName, getName())) {
            return PRIORITY_DEFAULT;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }

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

package liquibase.command;

import liquibase.AbstractExtensibleObject;
import liquibase.ObjectMetaData;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.exception.CommandExecutionException;
import liquibase.util.ObjectUtil;

import java.util.Objects;

/**
 * Base class for {@link LiquibaseCommand} implementations
 */
public abstract class AbstractLiquibaseCommand<T extends CommandResult> extends AbstractExtensibleObject implements LiquibaseCommand<T> {

    @Override
    public int getPriority(String commandName, Scope scope) {
        if (commandName != null && commandName.equalsIgnoreCase(getName())) {
            return PRIORITY_DEFAULT;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }

    /**
     * Default implementation checks the {@link liquibase.ObjectMetaData.Attribute#required} metadata associated with each attribute from {@link #getObjectMetaData()}
     */
    @Override
    public ValidationErrors validate(Scope scope) {
        ValidationErrors validationErrors = new ValidationErrors(this);

        for (ObjectMetaData.Attribute attr : getObjectMetaData().attributes) {
            if (ObjectUtil.defaultIfNull(attr.required, false)) {
                validationErrors.checkRequiredFields(attr.name);
            }
        }
        return validationErrors;
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

package liquibase.command;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Manages {@link LiquibaseCommand} implementations.
 */
public class CommandFactory extends AbstractPluginFactory<LiquibaseCommand> {

    protected CommandFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<LiquibaseCommand> getPluginClass() {
        return LiquibaseCommand.class;
    }

    @Override
    protected int getPriority(LiquibaseCommand obj, Scope scope, Object... args) {
        return obj.getPriority(((String) args[0]).toLowerCase(), scope);
    }

    public LiquibaseCommand getCommand(String commandName, Scope scope) {
        return getPlugin(scope, commandName);
    }

    public SortedSet<String> getAllCommandNames() {
        SortedSet<String> returnSet = new TreeSet<>();
        for (LiquibaseCommand command : findAllInstances()) {
            returnSet.add(command.getName());
        }

        return returnSet;
    }
}

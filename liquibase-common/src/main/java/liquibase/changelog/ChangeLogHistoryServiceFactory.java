package liquibase.changelog;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Manages {@link ChangeLogHistoryService} instances.
 */
public class ChangeLogHistoryServiceFactory extends AbstractPluginFactory<ChangeLogHistoryService> {

    protected ChangeLogHistoryServiceFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<ChangeLogHistoryService> getPluginClass() {
        return ChangeLogHistoryService.class;
    }

    @Override
    protected int getPriority(ChangeLogHistoryService obj, Scope scope, Object... args) {
        return obj.getPriority(scope);
    }

    /**
     * Creates and return the best {@link ChangeLogHistoryService} instance for the given scope.
     */
    public ChangeLogHistoryService getChangeLogHistoryService(Scope scope) {
        return getPlugin(scope);
    }
}

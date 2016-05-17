package liquibase.lockservice;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

public class LockServiceFactory extends AbstractPluginFactory<LockService> {

    protected LockServiceFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<LockService> getPluginClass() {
        return LockService.class;
    }

    @Override
    protected int getPriority(LockService obj, Scope scope, Object... args) {
        return obj.getPriority(scope);
    }

    /**
     * Creates and return the best {@link LockService} instance for the given scope.
     */
    public LockService getLockService(Scope scope) {
        return getPlugin(scope);
    }
}

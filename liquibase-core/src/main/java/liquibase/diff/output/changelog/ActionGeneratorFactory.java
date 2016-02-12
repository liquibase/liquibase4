package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.SingletonService;
import liquibase.action.Action;
import liquibase.servicelocator.AbstractServiceFactory;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;

import java.util.List;

public class ActionGeneratorFactory extends AbstractServiceFactory<ActionGenerator> implements SingletonService {

    /**
     * Protected because should be a singleton
     */
    protected ActionGeneratorFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<ActionGenerator> getServiceClass() {
        return ActionGenerator.class;
    }

    @Override
    protected int getPriority(ActionGenerator obj, Scope scope, Object... args) {
        return obj.getPriority((Class<? extends LiquibaseObject>) args[1], (Snapshot) args[2], (Snapshot) args[2], scope);
    }

    public List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        MissingObjectActionGenerator generator = getGenerator(MissingObjectActionGenerator.class, missingObject.getClass(), referenceSnapshot, targetSnapshot, scope);
        return generator.fixMissing(missingObject, referenceSnapshot, targetSnapshot, scope);
    }

    protected MissingObjectActionGenerator getGenerator(Class<? extends ActionGenerator> generatorType, Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        return (MissingObjectActionGenerator) getService(scope, generatorType, objectType, referenceSnapshot);
    }

}

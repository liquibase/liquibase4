package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.action.Action;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;

import java.util.List;

/**
 * Factory for creating and executing the correct {@link ActionGenerator} implementations for given missing/unexpected/changed objects.
 */
public class ActionGeneratorFactory extends AbstractPluginFactory<ActionGenerator> implements SingletonObject {

    protected ActionGeneratorFactory(Scope scope) {
        super(scope);
    }

    @Override
    protected Class<ActionGenerator> getPluginClass() {
        return ActionGenerator.class;
    }

    @Override
    protected int getPriority(ActionGenerator obj, Scope scope, Object... args) {
        return obj.getPriority((Class<? extends LiquibaseObject>) args[1], (Snapshot) args[2], (Snapshot) args[2], scope);
    }

    /**
     * Returns the list of {@link Action}s necessary to fix the passed missing object.
     * The reference and target snapshots are included for reference, in case any information in them are needed to determine the returned actions.
     */
    public List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        MissingObjectActionGenerator generator = getGenerator(MissingObjectActionGenerator.class, missingObject.getClass(), referenceSnapshot, targetSnapshot, scope);
        return generator.fixMissing(missingObject, referenceSnapshot, targetSnapshot, scope);
    }

    protected MissingObjectActionGenerator getGenerator(Class<? extends ActionGenerator> generatorType, Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        return (MissingObjectActionGenerator) getPlugin(scope, generatorType, objectType, referenceSnapshot);
    }

}

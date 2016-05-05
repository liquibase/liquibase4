package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.action.Action;
import liquibase.item.core.Column;
import liquibase.item.core.Table;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;

import java.util.ArrayList;
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
        return obj.getPriority((Class<? extends Item>) args[1], (Snapshot) args[2], (Snapshot) args[2], scope);
    }

    /**
     * Fixes the targetSnapshot to look like the referenceSnapshot
     */
    public List<? extends Action> fix(Snapshot targetSnapshot, Snapshot referenceSnapshot, Scope scope) {
        List<Action> returnList = new ArrayList<>();
        for (Class<? extends Item> type : new Class[]{Table.class}) {
            for (Item item : referenceSnapshot.get(type)) {
                Item targetItem = targetSnapshot.get(item.getClass(), item.toReference());
                if (targetItem == null) {
                    returnList.addAll(fixMissing(item, referenceSnapshot, targetSnapshot, scope));
                }
            }
        }

        return returnList;
    }

    /**
     * Returns the list of {@link Action}s necessary to fix the passed missing object.
     * The reference and target snapshots are included for reference, in case any information in them are needed to determine the returned actions.
     */
    public List<? extends Action> fixMissing(Item missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        MissingObjectActionGenerator generator = getGenerator(MissingObjectActionGenerator.class, missingObject.getClass(), referenceSnapshot, targetSnapshot, scope);
        return generator.fixMissing(missingObject, referenceSnapshot, targetSnapshot, scope);
    }

    protected MissingObjectActionGenerator getGenerator(Class<? extends ActionGenerator> generatorType, Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        return (MissingObjectActionGenerator) getPlugin(scope, generatorType, objectType, referenceSnapshot);
    }

}

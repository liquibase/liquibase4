package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.structure.LiquibaseObject;

/**
 * Convenience abstract base class for {@link SnapshotObjectsAction} related logic.
 * Implementations should not directly execute the metadata read, but instead return a {@link liquibase.actionlogic.DelegateResult} that returns simple lower-level actions.
 * This pattern is built into this methods {@link #execute(liquibase.action.Action, liquibase.Scope)} method.
 */
public abstract class AbstractSnapshotObjectsLogic<T extends SnapshotObjectsAction> extends AbstractActionLogic<T> {

    @Override
    protected Class<T> getSupportedAction() {
        return (Class<T>) SnapshotObjectsAction.class;
    }

    @Override
    public int getPriority(T action, Scope scope) {
        int priority = super.getPriority(action, scope);
        if (priority == PRIORITY_NOT_APPLICABLE) {
            return priority;
        }

        if (action.relatedTo == null || action.relatedTo.type == null || action.typeToSnapshot == null ) {
            return PRIORITY_NOT_APPLICABLE;
        }

        if (action.typeToSnapshot.isAssignableFrom(getTypeToSnapshot())) {
            for (Class clazz : getSupportedRelatedTypes()) {
                if (clazz.isAssignableFrom(action.relatedTo.type)) {
                    return priority;
                }
            }
        }

        return PRIORITY_NOT_APPLICABLE;
    }

    /**
     * Return the type of object this logic implementation supports.
     * Used in {@link #getPriority(liquibase.action.Action, liquibase.Scope)}.
     */
    abstract protected Class <? extends LiquibaseObject> getTypeToSnapshot();

    /**
     * Return the type(s) of {@link SnapshotObjectsAction#relatedTo} objects this implementation supports.
     * Used in {@link #getPriority(liquibase.action.Action, liquibase.Scope)}.
     */
    abstract protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes();

}

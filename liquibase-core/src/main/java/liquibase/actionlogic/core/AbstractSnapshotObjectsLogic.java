package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.CompoundResult;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Convenience abstract base class for {@link SnapshotObjectsAction} related logic.
 * Implementations should not directly execute the metadata read, but instead return a {@link liquibase.actionlogic.DelegateResult} that returns simple lower-level actions.
 * This pattern is built into this methods {@link #execute(liquibase.action.Action, liquibase.Scope)} method.
 * Returns PRIORITY_NOT_APPLICABLE if SnapshotObjectsAction.relatedTo has multiple values which are of different types.
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

        if (action.relatedTo == null || action.relatedTo.size() == 0 || action.typeToSnapshot == null ) {
            return PRIORITY_NOT_APPLICABLE;
        }

        Class<? extends LiquibaseObject> type = null;
        for (ObjectReference relatedTo : action.relatedTo) {
            if (type == null) {
                type = relatedTo.type;
            } else if (!type.equals(relatedTo.type)) { //more than one type, don't support that
                return PRIORITY_NOT_APPLICABLE;
            }
        }
         if (type == null) {
             return PRIORITY_NOT_APPLICABLE;
         }

        if (action.typeToSnapshot.isAssignableFrom(getTypeToSnapshot())) {
            for (Class clazz : getSupportedRelatedTypes()) {
                if (clazz.isAssignableFrom(type)) {
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


    @Override
    public final ActionResult execute(T action, Scope scope) throws ActionPerformException {
        if (action.relatedTo.size() == 1) {
            return execute(action.relatedTo.iterator().next(), action, scope);
        }

        LinkedHashMap<Action, ActionResult> result = new LinkedHashMap<>();
        for (ObjectReference relatedTo : action.relatedTo) {
            SnapshotObjectsAction cloneAction = (SnapshotObjectsAction) action.clone();
            cloneAction.relatedTo = new HashSet<>(Collections.singleton(relatedTo));
            result.put(cloneAction, execute(action.relatedTo.iterator().next(), action, scope));
        }
        return new CompoundResult(result);
    }

    /**
     * Called by {@link #execute(SnapshotObjectsAction, Scope)} for each relatedTo in SnapshotObjectsAction
     */
    public abstract ActionResult execute(ObjectReference relatedTo, T action, Scope scope) throws ActionPerformException;
}

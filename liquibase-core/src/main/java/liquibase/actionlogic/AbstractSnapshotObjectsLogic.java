package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

import java.util.*;

/**
 * Convenience abstract base class for {@link SnapshotObjectsAction} related logic.
 * Implementations should not directly execute the metadata read, but instead return a {@link liquibase.actionlogic.DelegateResult} that returns simple lower-level actions.
 * This pattern is built into this methods {@link #execute(liquibase.action.Action, liquibase.Scope)} method.
 * Returns {@link liquibase.servicelocator.Service#PRIORITY_NOT_APPLICABLE} if {@link SnapshotObjectsAction#relatedTo} has multiple values which are of different types.
 */
public abstract class AbstractSnapshotObjectsLogic<Action extends SnapshotObjectsAction, ObjectType extends LiquibaseObject> extends AbstractActionLogic<Action> {

    @Override
    protected Class<Action> getSupportedAction() {
        return (Class<Action>) SnapshotObjectsAction.class;
    }

    /**
     * Returns the priority of this ActionLogic implementation. Normally subclasses should override {@link #getTypeToSnapshot()} or {@link #getSupportedRelatedTypes()}
     */
    @Override
    public int getPriority(Action action, Scope scope) {
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
    protected abstract Class <ObjectType> getTypeToSnapshot();

    /**
     * Return the type(s) of {@link SnapshotObjectsAction#relatedTo} objects this implementation supports.
     * Used in {@link #getPriority(liquibase.action.Action, liquibase.Scope)}.
     */
    protected abstract Class<? extends LiquibaseObject>[] getSupportedRelatedTypes();


    /**
     * Calls {@link #execute(ObjectReference, SnapshotObjectsAction, Scope)} for each ObjectReference in {@link SnapshotObjectsAction#relatedTo}.
     * If there are more than one relatedTo objects, this will return a {@link CompoundResult}.
     * Normally subclasses should extend {@link #execute(ObjectReference, SnapshotObjectsAction, Scope)} rather than this method.
     */
    @Override
    public ActionResult execute(Action action, Scope scope) throws ActionPerformException {
        if (action.relatedTo.size() == 1) {
            return execute(action.relatedTo.iterator().next(), action, scope);
        }

        List<ActionResult> result = new ArrayList<>();
        for (ObjectReference relatedTo : action.relatedTo) {
            result.add(execute(relatedTo, action, scope));
        }
        return new CompoundResult(action, result);
    }

    /**
     * Called by {@link #execute(SnapshotObjectsAction, Scope)} for each relatedTo in SnapshotObjectsAction
     */
    public abstract ActionResult execute(ObjectReference relatedTo, Action action, Scope scope) throws ActionPerformException;
}

package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience abstract base class for {@link SnapshotItemsAction} related logic.
 * Implementations should not directly execute the metadata read, but instead return a {@link liquibase.actionlogic.DelegateResult} that returns simple lower-level actions.
 * This pattern is built into this methods {@link #execute(liquibase.action.Action, liquibase.Scope)} method.
 * Returns {@link Plugin#PRIORITY_NOT_APPLICABLE} if {@link SnapshotItemsAction#relatedTo} has multiple values which are of different types.
 */
public abstract class AbstractSnapshotItemsLogic<Action extends SnapshotItemsAction, ObjectType extends Item> extends AbstractActionLogic<Action> {

    @Override
    protected Class<Action> getSupportedAction() {
        return (Class<Action>) SnapshotItemsAction.class;
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

        Class<? extends Item> type = null;
        for (ItemReference relatedTo : action.relatedTo) {
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
     * Return the type(s) of {@link SnapshotItemsAction#relatedTo} objects this implementation supports.
     * Used in {@link #getPriority(liquibase.action.Action, liquibase.Scope)}.
     */
    protected abstract Class<? extends Item>[] getSupportedRelatedTypes();


    /**
     * Calls {@link #execute(ItemReference, SnapshotItemsAction, Scope)} for each {@link ItemReference} in {@link SnapshotItemsAction#relatedTo}.
     * If there are more than one relatedTo objects, this will return a {@link CompoundResult}.
     * Normally subclasses should extend {@link #execute(ItemReference, SnapshotItemsAction, Scope)} rather than this method.
     */
    @Override
    public ActionResult execute(Action action, Scope scope) throws ActionPerformException {
        if (action.relatedTo.size() == 1) {
            return execute(action.relatedTo.iterator().next(), action, scope);
        }

        List<ActionResult> result = new ArrayList<>();
        for (ItemReference relatedTo : action.relatedTo) {
            result.add(execute(relatedTo, action, scope));
        }
        return new CompoundResult(action, result);
    }

    /**
     * Called by {@link #execute(SnapshotItemsAction, Scope)} for each relatedTo in the action.
     */
    public abstract ActionResult execute(ItemReference relatedTo, Action action, Scope scope) throws ActionPerformException;
}

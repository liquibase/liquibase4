package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.exception.ActionPerformException;
import liquibase.plugin.Plugin;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for SnapshotObjectsLogic implementations that read from an online database.
 */
public  abstract class AbstractSnapshotDatabaseObjectsLogic<ObjectType extends LiquibaseObject> extends AbstractSnapshotObjectsLogic<SnapshotObjectsAction, ObjectType> {

    /**
     * Only requires a {@link DatabaseConnection}, but will test that it is not an OfflineConnection in {@link #getPriority(SnapshotObjectsAction, Scope)}
     */
    @Override
    protected Class<? extends DatabaseConnection> getRequiredConnection() {
        return DatabaseConnection.class;
    }

    /**
     * Besides standard logic, will return {@link Plugin#PRIORITY_NOT_APPLICABLE} if it is an offline connection.
     */
    @Override
    public int getPriority(SnapshotObjectsAction action, Scope scope) {
        int priority = super.getPriority(action, scope);

        if (priority > PRIORITY_NOT_APPLICABLE) {
            if (scope.getDatabase().getConnection() instanceof OfflineConnection) {
                return PRIORITY_NOT_APPLICABLE;
            }
        }
        return priority;
    }

    /**
     * Default implementation returns a {@link liquibase.actionlogic.DelegateResult} based on {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)}  and {@link #createModifier(ObjectReference, SnapshotObjectsAction, Scope)}.
     */
    @Override
    public ActionResult execute(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, createModifier(relatedTo, action, scope), createSnapshotAction(relatedTo, action, scope));

    }

    /**
     * Return a lower-level action that will snapshot given type relatedTo object for the action.
     * This action will be included in the {@link DelegateResult} in {@link #execute(ObjectReference, SnapshotObjectsAction, Scope)}
     */
    protected abstract liquibase.action.Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException;

    /**
     * Returns a {@link DelegateResult.Modifier} that will convert the raw results from the action returned by {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)} to a more standard form.
     * This method is used by {@link #execute(ObjectReference, SnapshotObjectsAction, Scope)}.
     * Default implementation returns {@link RowsToObjectsModifier} which uses {@link #convertToObject(Object, ObjectReference, SnapshotObjectsAction, Scope)}
     * to convert the returned QueryResult to the correct DatabaseObject.
     * <br><br>
     * The passed action is the original action, not the one returned by {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)}
     */
    protected DelegateResult.Modifier createModifier(ObjectReference relatedTo, SnapshotObjectsAction originalAction, final Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope);
    }

    /**
     * Used by {@link RowsToObjectsModifier} to convert a row returned by the generated action into the final object type.
     * This method is in this class vs. on SnapshotModifier to avoid an extra subclass to create.
     */
    protected abstract ObjectType convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException;

    /**
     * Class used by default {@link #createModifier(ObjectReference, SnapshotObjectsAction, Scope)} implementation.
     */
    protected class RowsToObjectsModifier implements DelegateResult.Modifier {

        private ObjectReference relatedTo;
        private SnapshotObjectsAction originalAction;
        private Scope scope;

        public RowsToObjectsModifier(ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) {
            this.relatedTo = relatedTo;
            this.originalAction = originalAction;
            this.scope = scope;
        }

        public SnapshotObjectsAction getOriginalAction() {
            return originalAction;
        }

        public ObjectReference getRelatedTo() {
            return relatedTo;
        }

        public Scope getScope() {
            return scope;
        }

        /**
         * Expects ActionResult to be a {@link RowBasedQueryResult} or a {@link CompoundResult} with just a single RowBasedQueryResult.
         * Iterates over each row and calls {@link #convertToObject(Object, ObjectReference, SnapshotObjectsAction, Scope)}, then returns a new
         * {@link ObjectBasedQueryResult} containing the converted objects.
         */
        @Override
        public ActionResult rewrite(ActionResult result) throws ActionPerformException {
            if (result instanceof CompoundResult) {
                List<ActionResult> flatResults = ((CompoundResult) result).getFlatResults();
                if (flatResults.size() != 1) {
                    throw new ActionPerformException("Expected 1 ActionResult, got "+flatResults.size());
                }
                result = flatResults.get(0);
                if (!(result instanceof RowBasedQueryResult)) {
                    throw new ActionPerformException("Expected RowBasedQueryResult, got "+result.getClass().getName());
                }
            }

            List<LiquibaseObject> liquibaseObjects = new ArrayList<>();
            for (RowBasedQueryResult.Row row : ((RowBasedQueryResult) result).getRows()) {
                LiquibaseObject object = convertToObject(row, getRelatedTo(), getOriginalAction(), getScope());
                if (object != null) {
                    liquibaseObjects.add(object);
                }
            }

            return new ObjectBasedQueryResult(originalAction, liquibaseObjects);
        }
    }


}

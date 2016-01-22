package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.*;
import liquibase.database.DatabaseConnection;
import liquibase.database.JdbcConnection;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSnapshotObjectsLogicJdbc<T extends SnapshotObjectsAction> extends AbstractSnapshotObjectsLogic<T> {

    @Override
    protected Class<? extends DatabaseConnection> getRequiredConnection() {
        return JdbcConnection.class;
    }


    /**
     * Default implementation returns a {@link liquibase.actionlogic.DelegateResult} based on {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)}  and {@link #createModifier(ObjectReference, SnapshotObjectsAction, Scope)}.
     */
    @Override
    public ActionResult execute(ObjectReference relatedTo, T action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, createModifier(relatedTo, action, scope), createSnapshotAction(relatedTo, action, scope));

    }

    /**
     * Return a lower-level action that will snapshot given type of objects that are related to the given object.
     * The QueryResult from the Action returned by this method will be fed through the object returned by {@link #createModifier(ObjectReference, SnapshotObjectsAction, Scope)}.
     */
    protected abstract Action createSnapshotAction(ObjectReference relatedTo, T action, Scope scope) throws ActionPerformException;

    /**
     * Returns a {@link liquibase.actionlogic.ActionResult.Modifier} that will convert the raw results from the action returned by {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)}
     * to a list of objects.
     * Default implementation returns {@link AbstractSnapshotObjectsLogicJdbc.SnapshotModifier} which uses {@link #convertToObject(Object, ObjectReference, SnapshotObjectsAction, Scope)}
     * to convert the returned QueryResult to the correct DatabaseObject.
     * <p/>
     * The passed action is the original action, not the one returned by {@link #createSnapshotAction(ObjectReference, SnapshotObjectsAction, Scope)}
     */
    protected ActionResult.Modifier createModifier(ObjectReference relatedTo, T originalAction, final Scope scope) {
        return new SnapshotModifier(relatedTo, originalAction, scope);
    }

    /**
     * Converts a row returned by the generated action into the final object type.
     */
    protected abstract LiquibaseObject convertToObject(Object object, ObjectReference relatedTo, T originalAction, Scope scope) throws ActionPerformException;

    /**
     * Called for each DatabaseObject in {@link SnapshotModifier#rewrite(liquibase.actionlogic.ActionResult)} to "fix" any raw data coming back from the database.
     * Default implementation trims object name to null.
     */
    protected void correctObject(LiquibaseObject object) {
        String name = object.getName();
        if (name != null) {
            object.set("name", StringUtils.trimToNull(name));
        }
    }

    /**
     * Class used by default {@link #createModifier(ObjectReference, SnapshotObjectsAction, Scope)} implementation.
     */
    protected class SnapshotModifier implements ActionResult.Modifier {

        private ObjectReference relatedTo;
        private T originalAction;
        private Scope scope;

        public SnapshotModifier(ObjectReference relatedTo, T originalAction, Scope scope) {
            this.relatedTo = relatedTo;
            this.originalAction = originalAction;
            this.scope = scope;
        }

        public T getOriginalAction() {
            return originalAction;
        }

        public ObjectReference getRelatedTo() {
            return relatedTo;
        }

        public Scope getScope() {
            return scope;
        }

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
                    correctObject(object);
                    liquibaseObjects.add(object);
                }
            }

            return new ObjectBasedQueryResult(liquibaseObjects, originalAction);
        }

    }


}

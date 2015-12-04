package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.actionlogic.RowBasedQueryResult;
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
     * Default implementation returns a {@link liquibase.actionlogic.DelegateResult} based on {@link #createSnapshotAction(T, liquibase.Scope)}  and {@link #createModifier(T, liquibase.Scope)}.
     */
    @Override
    public ActionResult execute(T action, Scope scope) throws ActionPerformException {
        return new DelegateResult(createModifier(action, scope), createSnapshotAction(action, scope));

    }

    /**
     * Return a lower-level action that will snapshot given type of objects that are related to the given object.
     * The QueryResult from the Action returned by this method will be fed through the object returned by {@link #createModifier(T, liquibase.Scope)}.
     */
    protected abstract Action createSnapshotAction(T action, Scope scope) throws ActionPerformException;

    /**
     * Returns a {@link liquibase.actionlogic.ActionResult.Modifier} that will convert the raw results from the action returned by {@link #createSnapshotAction(T, liquibase.Scope)}
     * to a list of objects.
     * Default implementation returns {@link AbstractSnapshotObjectsLogicJdbc.SnapshotModifier} which uses {@link #convertToObject(liquibase.actionlogic.RowBasedQueryResult.Row, T, liquibase.Scope)}
     * to convert the returned QueryResult to the correct DatabaseObject.
     *
     * The passed action is the original action, not the one returned by {@link #createSnapshotAction(T, liquibase.Scope)}
     */
    protected ActionResult.Modifier createModifier(T originalAction, final Scope scope) {
        return new SnapshotModifier(originalAction, scope);
    }

    /**
     * Converts a row returned by the generated action into the final object type.
     */
    protected abstract LiquibaseObject convertToObject(RowBasedQueryResult.Row row, T originalAction, Scope scope) throws ActionPerformException;

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
     * Class used by default {@link #createModifier(T, liquibase.Scope)} implementation.
     */
    protected class SnapshotModifier implements ActionResult.Modifier {

        private T originalAction;
        private Scope scope;

        public SnapshotModifier(T originalAction, Scope scope) {
            this.originalAction = originalAction;
            this.scope = scope;
        }

        public T getOriginalAction() {
            return originalAction;
        }

        public Scope getScope() {
            return scope;
        }

        @Override
        public ActionResult rewrite(ActionResult result) throws ActionPerformException {
            List<LiquibaseObject> liquibaseObjects = new ArrayList<LiquibaseObject>();
            for (RowBasedQueryResult.Row row : ((RowBasedQueryResult) result).getRows()) {
                LiquibaseObject object = convertToObject(row, getOriginalAction(), getScope());
                correctObject(object);
                liquibaseObjects.add(object);
            }

            return new ObjectBasedQueryResult(liquibaseObjects);
        }

    }


}

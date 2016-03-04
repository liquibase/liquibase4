package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.SetRemarksAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.SnapshotForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObject;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class SetRemarksLogicH2 extends AbstractActionLogic<SetRemarksAction> {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Class<? extends SetRemarksAction> getSupportedAction() {
        return SetRemarksAction.class;
    }

    @Override
    public ValidationErrors validate(SetRemarksAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("object", "object.type");
    }

    @Override
    public int getPriority(SetRemarksAction action, Scope scope) {
        if (action.object != null && action.object.type != null && !Table.class.isAssignableFrom(action.object.type)
                && !View.class.isAssignableFrom(action.object.type)
                && !Column.class.isAssignableFrom(action.object.type)
                && !Index.class.isAssignableFrom(action.object.type)
                && !Schema.class.isAssignableFrom(action.object.type)
                && !Sequence.class.isAssignableFrom(action.object.type)
                ) {
            return PRIORITY_NOT_APPLICABLE;
        }
        return super.getPriority(action, scope);
    }

    @Override
    public ActionResult execute(SetRemarksAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        String objectName = database.quoteObjectName(action.object, scope);
        if (action.object.instanceOf(Column.class)) {
            objectName = database.quoteObjectName((DatabaseObjectReference) action.object.container, scope) + "." + objectName;
        }

        return new DelegateResult(action, null, new ExecuteSqlAction(new StringClauses()
                .append("COMMENT ON")
                .append(action.object.type.getSimpleName().toUpperCase())
                .append(objectName)
                .append("IS")
                .append(action.remarks == null ? "NULL" : database.quoteString(action.remarks, scope))));
    }

    @Override
    public ActionStatus checkStatus(SetRemarksAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            DatabaseObject item = scope.getSingleton(SnapshotFactory.class).snapshot(action.object, scope);
            if (action.remarks == null) {
                status.assertApplied(item.getRemarks() == null, "Remarks were not set");
            } else {
                status.assertApplied(action.remarks.equals(item.getRemarks()), "Remarks were not applied");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
        return status;

    }
}

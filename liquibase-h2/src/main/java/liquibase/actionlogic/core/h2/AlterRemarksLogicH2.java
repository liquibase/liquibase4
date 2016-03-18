package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.AlterRemarksAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObject;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.core.*;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class AlterRemarksLogicH2 extends AbstractActionLogic<AlterRemarksAction> {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Class<? extends AlterRemarksAction> getSupportedAction() {
        return AlterRemarksAction.class;
    }

    @Override
    public ValidationErrors validate(AlterRemarksAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("object", "object.type", "object.name");
    }

    @Override
    public ActionResult execute(AlterRemarksAction action, Scope scope) throws ActionPerformException {
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
    public ActionStatus checkStatus(AlterRemarksAction action, Scope scope) {
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

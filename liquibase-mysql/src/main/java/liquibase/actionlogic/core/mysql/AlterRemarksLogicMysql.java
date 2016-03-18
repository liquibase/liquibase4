package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.AlterRemarksAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObject;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Table;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

/**
 * Currently only supports setting comments on tables because adding it to columns requires knowing the column definition.
 */
public class AlterRemarksLogicMysql extends AbstractActionLogic<AlterRemarksAction> {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Class<? extends AlterRemarksAction> getSupportedAction() {
        return AlterRemarksAction.class;
    }

    @Override
    public ValidationErrors validate(AlterRemarksAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("object", "object.type");

        errors.checkField("object.type", new ValidationErrors.FieldCheck<Class>() {
            @Override
            public String check(Class obj) {
                if (!Table.class.isAssignableFrom(obj)) {
                    return "only supports tables";
                }
                return null;
            }
        });
        return errors;
    }

    @Override
    public ActionResult execute(AlterRemarksAction action, Scope scope) throws ActionPerformException {
        String remarks;
        if (action.remarks == null) {
            remarks = "''";
        } else {
            remarks = scope.getDatabase().quoteString(action.remarks, scope);
        }

        if (action.object.instanceOf(Table.class)) {
            return new DelegateResult(action, null, new AlterTableAction((RelationReference) action.object, new StringClauses().append("COMMENT").append(remarks)));
        } else {
            throw new UnexpectedLiquibaseException("Unexpected object type: " + action.object.type.getName());
        }
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

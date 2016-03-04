package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropColumnsAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropColumnsLogic extends AbstractActionLogic<DropColumnsAction> {

    @Override
    protected Class<DropColumnsAction> getSupportedAction() {
        return DropColumnsAction.class;
    }

    @Override
    public ValidationErrors validate(DropColumnsAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("columns", "columns.name", "columns.container");
    }

    @Override
    public ActionResult execute(DropColumnsAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();
        for (ColumnReference column : CollectionUtil.createIfNull(action.columns)) {
            actions.add(new AlterTableAction(
                    column.getRelation(),
                    generateSql(column, action, scope)
            ));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    @Override
    public ActionStatus checkStatus(DropColumnsAction action, Scope scope) {
        ActionStatus actionStatus = new ActionStatus();
        try {
            for (ColumnReference column : action.columns) {
                actionStatus.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(column, scope) == null, "Column " + column + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return actionStatus.unknown(e);
        }
        return actionStatus;
    }

    protected StringClauses generateSql(ColumnReference column, DropColumnsAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("DROP COLUMN")
                .append(database.quoteObjectName(column.name, Column.class, scope));
    }
}

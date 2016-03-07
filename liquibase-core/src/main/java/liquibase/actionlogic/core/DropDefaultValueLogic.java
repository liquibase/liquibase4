package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.DropDefaultValueAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class DropDefaultValueLogic extends AbstractSqlBuilderLogic<DropDefaultValueAction> {

    @Override
    protected Class<DropDefaultValueAction> getSupportedAction() {
        return DropDefaultValueAction.class;
    }

    @Override
    public ValidationErrors validate(DropDefaultValueAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("column", "column.name",
                        "column.container", "column.container.name");
    }

    @Override
    public ActionStatus checkStatus(DropDefaultValueAction action, Scope scope) {
        try {
            Column snapshotColumn = scope.getSingleton(SnapshotFactory.class).snapshot(Column.class, action.column, scope);
            return new ActionStatus().assertCorrect(snapshotColumn.defaultValue == null, "Default value was not null but "+snapshotColumn.defaultValue);
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(DropDefaultValueAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterColumnAction(
                action.column,
                generateSql(action, scope)));
    }

    @Override
    protected StringClauses generateSql(DropDefaultValueAction action, Scope scope) {
        return new StringClauses().append("DROP DEFAULT");
    }
}

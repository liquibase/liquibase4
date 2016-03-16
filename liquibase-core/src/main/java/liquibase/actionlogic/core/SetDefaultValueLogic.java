package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.SetDefaultValueAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.datatype.DataTypeLogic;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class SetDefaultValueLogic extends AbstractSqlBuilderLogic<SetDefaultValueAction> {

    public enum Clauses {
        dataType,
        defaultValue,
    }

    @Override
    protected Class<SetDefaultValueAction> getSupportedAction() {
        return SetDefaultValueAction.class;
    }

    @Override
    public ValidationErrors validate(SetDefaultValueAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("column", "column.name",
                        "column.container", "column.container.name");
    }

    @Override
    public ActionStatus checkStatus(SetDefaultValueAction action, Scope scope) {
        try {
            Column snapshotColumn = scope.getSingleton(SnapshotFactory.class).snapshot(Column.class, action.column, scope);
            return new ActionStatus().assertPropertyCorrect(snapshotColumn, action, "defaultValue");
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(SetDefaultValueAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterColumnAction(
                action.column,
                generateSql(action, scope)));
    }

    @Override
    protected StringClauses generateSql(SetDefaultValueAction action, Scope scope) {
        if (action.defaultValue == null) {
            return new StringClauses().append("DROP DEFAULT");
        } else {
            DataTypeLogic dataTypeLogic = scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(action.columnDataType, scope);
            return new StringClauses()
                    .append("SET DEFAULT")
                    .append(Clauses.defaultValue, dataTypeLogic.toSql(action.defaultValue, action.columnDataType, scope));
        }
    }
}

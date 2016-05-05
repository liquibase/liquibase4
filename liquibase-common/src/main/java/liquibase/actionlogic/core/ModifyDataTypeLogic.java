package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.ModifyDataTypeAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class ModifyDataTypeLogic extends AbstractActionLogic<ModifyDataTypeAction> {

    @Override
    protected Class<? extends ModifyDataTypeAction> getSupportedAction() {
        return ModifyDataTypeAction.class;
    }

    @Override
    public ValidationErrors validate(ModifyDataTypeAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("column", "column.name", "column.container")
                .checkRequiredFields("newDataType");
    }

    @Override
    public ActionStatus checkStatus(ModifyDataTypeAction action, Scope scope) {
        try {
            Column column = scope.getSingleton(SnapshotFactory.class).snapshot(action.column, scope);
            ActionStatus status = new ActionStatus();
            if (column == null) {
                return status.unknown("Could not find column "+action.column);
            }
            return status.assertApplied(column.type.toString().equals(action.newDataType.toString()), "Incorrect column type. Expected "+action.newDataType+" but found "+column.type);
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(ModifyDataTypeAction action, Scope scope) throws ActionPerformException {
        String dataTypeSql = scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(action.newDataType, scope).toSql(action.newDataType, scope);
        return new DelegateResult(action, null, new AlterColumnAction(action.column, new StringClauses().append(dataTypeSql)));
    }
}

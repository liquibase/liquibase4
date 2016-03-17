package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.UpdateSqlAction;
import liquibase.action.core.UpdateDataAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.util.StringClauses;

public class UpdateDataLogic extends AbstractActionLogic<UpdateDataAction> {

    public enum Clauses {
        columns,
        whereClause,
    }

    @Override
    protected Class<? extends UpdateDataAction> getSupportedAction() {
        return UpdateDataAction.class;
    }

    @Override
    public ValidationErrors validate(UpdateDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("relation", "relation.name")
                .checkRequiredFields("columns", "columns.name");
    }

    @Override
    public ActionStatus checkStatus(UpdateDataAction action, Scope scope) {
        return new ActionStatus().assertCorrect(true, "Nothing to check");
    }

    @Override
    public ActionResult execute(UpdateDataAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new UpdateSqlAction(generateSql(action, scope)));
    }

    protected StringClauses generateSql(UpdateDataAction action, Scope scope) throws ActionPerformException {
        StringClauses columnClauses = new StringClauses(", ");
        Database database = scope.getDatabase();
        DataTypeLogicFactory typeLogicFactory = scope.getSingleton(DataTypeLogicFactory.class);

        for (UpdateDataAction.UpdatedColumn column : action.columns) {
            String value;
            if (column.value == null) {
                value = "NULL";
            } else {
                DataType dataType = DataType.forType(column.value.getClass());
                value = typeLogicFactory.getDataTypeLogic(dataType, scope).toSql(column.value, dataType, scope);
            }
            columnClauses.append(database.quoteObjectName(column.name, Column.class, scope) + "=" + value);
        }
        StringClauses returnSql = new StringClauses()
                .append("UPDATE")
                .append(database.quoteObjectName(action.relation, scope))
                .append("SET")
                .append(Clauses.columns, columnClauses);

        if (!action.where.isEmpty()) {
            returnSql.append("WHERE").append(Clauses.whereClause, action.where);
        }

        return returnSql;
    }

}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.ActionStatus;
import liquibase.action.core.AddAutoIncrementAction;
import liquibase.action.core.AlterColumnAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotFactory;
import liquibase.structure.core.Column;
import liquibase.util.StringClauses;

public class AddAutoIncrementLogic extends AbstractSqlBuilderLogic<AddAutoIncrementAction> {

    public enum Clauses {
        autoIncrement, dataType
    }

    @Override
    protected Class<AddAutoIncrementAction> getSupportedAction() {
        return AddAutoIncrementAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supportsAutoIncrement();
    }

    @Override
    public ValidationErrors validate(AddAutoIncrementAction action, Scope scope) {
        ValidationErrors validationErrors = super.validate(action, scope);
        validationErrors.checkRequiredFields("column", "column.container");

        return validationErrors;
    }

    @Override
    public ActionStatus checkStatus(AddAutoIncrementAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            Column column = scope.getSingleton(SnapshotFactory.class).snapshot(action.column, scope);
            if (column == null) return result.unknown("Column '"+action.column +"' does not exist");


            result.assertApplied(column.isAutoIncrement(), "Column '"+action.column +"' is not auto-increment");

//            if (column.autoIncrementInformation != null) {
//                result.assertPropertyCorrect(action, column.autoIncrementInformation, "startWith");
//                result.assertPropertyCorrect(action, column.autoIncrementInformation, "incrementBy");
//            }

            return result;
        } catch (Exception e) {
            return result.unknown(e);

        }
    }

    @Override
    public ActionResult execute(AddAutoIncrementAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterColumnAction(
                action.column,
                generateSql(action, scope)));
    }

    protected StringClauses generateSql(AddAutoIncrementAction action, Scope scope) {

        Database database = scope.getDatabase();

        StringClauses clauses = new StringClauses();
        if (action.dataType != null) {
            clauses.append(Clauses.dataType, action.dataType.toString()); //scope.getSingleton(DataTypeTranslatorFactory.class).getDataTypeLogic(scope).toSql(action.dataType, scope));
        }
        clauses.append(Clauses.autoIncrement, generateAutoIncrementClause(action.autoIncrementInformation));

        return clauses;
    }

    public StringClauses generateAutoIncrementClause(Column.AutoIncrementInformation autoIncrementInformation) {
        StringClauses clauses = new StringClauses().append("marker", "GENERATED BY DEFAULT AS IDENTITY");

        StringClauses autoIncrementDetails = null;
        if (autoIncrementInformation != null) {
            autoIncrementDetails = new StringClauses("(", " ", ")");
            if (autoIncrementInformation.startWith != null) {
                autoIncrementDetails.append("startWith", "START WITH "+autoIncrementInformation.startWith);
            }
            if (autoIncrementInformation.incrementBy != null) {
                autoIncrementDetails.append("incrementBy", "INCREMENT BY "+autoIncrementInformation.incrementBy);
            }
        }
        clauses.append("details", autoIncrementDetails);

        return clauses;

    }


}

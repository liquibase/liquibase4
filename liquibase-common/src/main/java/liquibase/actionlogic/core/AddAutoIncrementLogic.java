package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AddAutoIncrementAction;
import liquibase.action.core.AlterColumnAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class AddAutoIncrementLogic extends AbstractSqlBuilderLogic<AddAutoIncrementAction> {

    public enum Clauses {
        autoIncrement, dataType, startWith, autoIncrementMarker, autoIncrementParameters, incrementBy
    }

    @Override
    protected Class<AddAutoIncrementAction> getSupportedAction() {
        return AddAutoIncrementAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supports(Database.Feature.AUTO_INCREMENT, scope);
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
            if (column == null) {
                return result.add(ActionStatus.Status.unknown, "Column '"+action.column +"' does not exist");
            }

            result.assertApplied(column.isAutoIncrement(), "Column '"+action.column +"' is not auto-increment");

            return result;
        } catch (Exception e) {
            return result.unknown(e);

        }
    }

    /**
     * Returns an AlterColumnAction DelegateResult.
     */
    @Override
    public ActionResult execute(AddAutoIncrementAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterColumnAction(
                action.column,
                generateSql(action, scope)));
    }

    protected StringClauses generateSql(AddAutoIncrementAction action, Scope scope) {
        StringClauses clauses = new StringClauses();
        if (action.dataType != null) {
            clauses.append(Clauses.dataType, action.dataType.toString());
        }
        clauses.append(Clauses.autoIncrement, generateAutoIncrementClause(action.autoIncrementInformation));

        return clauses;
    }


    /**
     * Returns the SQL needed to make a column auto-increment.
     * Re-use this method anywhere you are marking a column as auto-increment.
     */
    public StringClauses generateAutoIncrementClause(Column.AutoIncrementInformation autoIncrementInformation) {
        StringClauses clauses = new StringClauses().append(Clauses.autoIncrementMarker, "GENERATED BY DEFAULT AS IDENTITY");

        StringClauses autoIncrementDetails = null;
        if (autoIncrementInformation != null) {
            autoIncrementDetails = new StringClauses("(", " ", ")");
            if (autoIncrementInformation.startWith != null) {
                autoIncrementDetails.append(Clauses.startWith, "START WITH "+autoIncrementInformation.startWith);
            }
            if (autoIncrementInformation.incrementBy != null) {
                autoIncrementDetails.append(Clauses.incrementBy, "INCREMENT BY "+autoIncrementInformation.incrementBy);
            }
        }
        clauses.append(Clauses.autoIncrementParameters, autoIncrementDetails);

        return clauses;
    }


}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.InsertDataAction;
import liquibase.actionlogic.*;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.RowData;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class InsertDataLogic extends AbstractActionLogic<InsertDataAction> {

    public enum Clauses {
        columns,
        valueList
    }

    @Override
    protected Class<? extends InsertDataAction> getSupportedAction() {
        return InsertDataAction.class;
    }

    @Override
    public ValidationErrors validate(InsertDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("data",
                        "data.relation", "data.relation.name");
    }

    @Override
    public ActionStatus checkStatus(InsertDataAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            DataTypeLogicFactory dataTypeLogicFactory = scope.getSingleton(DataTypeLogicFactory.class);

            for (RowData row : action.data) {
                SelectDataAction selectAction = new SelectDataAction(row.relation);
                for (RowData.Cell cell : row.data) {
                    selectAction.columns.add(new SelectDataAction.SelectedColumn(cell.columnName));
                    selectAction.addWhere(
                            scope.getDatabase().quoteObjectName(cell.columnName, Column.class, scope)
                                    + (cell.value == null ? " IS " : "=")
                                    + dataTypeLogicFactory.getDataTypeLogic(cell.type, scope).toSql(cell.value, cell.type, scope)
                    );
                }
                QueryResult query = scope.getSingleton(ActionExecutor.class).query(selectAction, scope);
                result.assertCorrect(query.size() > 0, "Did not find a row matching " + selectAction.where.toString());
            }
        } catch (ActionPerformException e) {
            result.unknown(e);
        }
        return result;
    }

    @Override
    public ActionResult execute(InsertDataAction action, Scope scope) throws ActionPerformException {
        List<ExecuteSqlAction> returnList = new ArrayList<>();
        for (RowData row : action.data) {
            returnList.add(new ExecuteSqlAction(generateSql(row, action, scope)));
        }

        return new DelegateResult(action, null, returnList.toArray(new Action[returnList.size()]));
    }

    protected StringClauses generateSql(RowData row, InsertDataAction action, Scope scope) {
        DataTypeLogicFactory dataTypeLogicFactory = scope.getSingleton(DataTypeLogicFactory.class);

        StringClauses columnsClause = new StringClauses("(", ", ", ")");
        StringClauses valueListClause = new StringClauses("(", ", ", ")");
        for (RowData.Cell cell : row.data) {
            columnsClause.append(scope.getDatabase().quoteObjectName(cell.columnName, Column.class, scope));
            valueListClause.append(dataTypeLogicFactory.getDataTypeLogic(cell.type, scope).toSql(cell.value, cell.type, scope));
        }

        return new StringClauses()
                .append("INSERT").append("INTO")
                .append(scope.getDatabase().quoteObjectName(row.relation, scope))
                .append(Clauses.columns, columnsClause)
                .append("VALUES")
                .append(Clauses.valueList, valueListClause);
    }
}

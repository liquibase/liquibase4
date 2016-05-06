package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.InsertDataAction;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.RowData;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class InsertDataLogic extends AbstractActionLogic<InsertDataAction> {

    public enum Clauses {
        mergeSelect,
        mergeWhere,
        insertColumns,
        updateColumns,
        valueList
    }

    @Override
    protected Class<? extends InsertDataAction> getSupportedAction() {
        return InsertDataAction.class;
    }

    @Override
    public ValidationErrors validate(final InsertDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("rows",
                        "rows.relation", "rows.relation.name")
                .check(new ValidationErrors.ErrorCheck() {
                    @Override
                    public String check() {
                        if (action.columnsForUpdateCheck.size() > 0) {
                            for (String columnForCheck : action.columnsForUpdateCheck) {
                                for (RowData row : action.rows) {
                                    for (String dataColumn : row.getColumnNames()) {
                                        if (!dataColumn.equals(columnForCheck)) {
                                            return null;
                                        }
                                    }
                                }
                            }
                            return "there must be at least one column in rowData not in columnsForUpdateCheck";
                        }
                        return null;
                    }
                });
    }

    @Override
    public ActionStatus checkStatus(InsertDataAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (RowData row : action.rows) {
                SelectDataAction selectAction = new SelectDataAction(row.relation);
                for (RowData.ColumnData columnData : row.columns) {
                    selectAction.columns.add(new SelectDataAction.SelectedColumn(columnData.name));
                    selectAction.addWhere(
                            scope.getDatabase().quoteObjectName(columnData.name, Column.class, scope)
                                    + (columnData.value == null ? " IS " : "=")
                                    + getCellType(columnData, scope).toSql(columnData.value, columnData.type, scope)
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

    protected DataTypeLogic getCellType(RowData.ColumnData columnData, Scope scope) {
        DataTypeLogicFactory dataTypeLogicFactory = scope.getSingleton(DataTypeLogicFactory.class);


        if (columnData.type == null) {
            if (columnData.value == null) {
                return dataTypeLogicFactory.getDataTypeLogic(null, scope);
            } else {
                return dataTypeLogicFactory.getDataTypeLogic(DataType.forType(columnData.value.getClass()), scope);
            }
        } else {
            return dataTypeLogicFactory.getDataTypeLogic(columnData.type, scope);
        }
    }

    @Override
    public ActionResult execute(InsertDataAction action, Scope scope) throws ActionPerformException {
        List<ExecuteSqlAction> returnList = new ArrayList<>();
        for (RowData row : action.rows) {
            if (action.columnsForUpdateCheck.size() > 0) {
                returnList.add(new ExecuteSqlAction(generateMergeSql(row, action, scope)));
            } else {
                returnList.add(new ExecuteSqlAction(generateInsertSql(row, action, scope)));
            }
        }

        return new DelegateResult(action, null, returnList.toArray(new Action[returnList.size()]));
    }

    protected StringClauses generateInsertSql(RowData row, InsertDataAction action, Scope scope) {
        StringClauses columnsClause = new StringClauses("(", ", ", ")");
        StringClauses valueListClause = new StringClauses("(", ", ", ")");
        for (RowData.ColumnData columnData : row.columns) {
            columnsClause.append(scope.getDatabase().quoteObjectName(columnData.name, Column.class, scope));
            valueListClause.append(getCellType(columnData, scope).toSql(columnData.value, columnData.type, scope));
        }

        return new StringClauses()
                .append("INSERT").append("INTO")
                .append(scope.getDatabase().quoteObjectName(row.relation, scope))
                .append(Clauses.insertColumns, columnsClause)
                .append("VALUES")
                .append(Clauses.valueList, valueListClause);
    }

    protected StringClauses generateMergeSql(RowData row, InsertDataAction action, Scope scope) {
        StringClauses mergeSelectClauses = new StringClauses(", ");
        StringClauses columnsClause = new StringClauses("(", ", ", ")");
        StringClauses updateColumnsClause = new StringClauses(", ");
        StringClauses valueListClause = new StringClauses("(", ", ", ")");
        Database database = scope.getDatabase();

        for (RowData.ColumnData columnData : row.columns) {
            String valueAsSql = getCellType(columnData, scope).toSql(columnData.value, columnData.type, scope);
            String quotedColumnName = database.quoteObjectName(columnData.name, Column.class, scope);

            mergeSelectClauses.append(valueAsSql +" as "+ quotedColumnName);

            columnsClause.append(quotedColumnName);
            valueListClause.append(valueAsSql);

            if (!action.columnsForUpdateCheck.contains(columnData.name)) {
                updateColumnsClause.append(quotedColumnName +"="+ valueAsSql);
            }
        }

        StringClauses onClause = new StringClauses("("," AND ",")");
        for (String column : action.columnsForUpdateCheck) {
            onClause.append("dst." + database.quoteObjectName(column, Column.class, scope) + "=src." + database.quoteObjectName(column, Column.class, scope));
        }

        return new StringClauses()
                .append("MERGE").append("INTO")
                .append(database.quoteObjectName(row.relation, scope)).append("dst")
                .append("USING")
                .append("(").append("SELECT").append(Clauses.mergeSelect, mergeSelectClauses).append(")").append("src")
                .append("ON").append(Clauses.mergeWhere, onClause)
                .append("WHEN MATCHED THEN UPDATE SET").append(Clauses.updateColumns, updateColumnsClause)
                .append("WHEN NOT MATCHED THEN INSERT").append(Clauses.insertColumns, columnsClause).append("VALUES").append(Clauses.valueList, valueListClause);
    }

}

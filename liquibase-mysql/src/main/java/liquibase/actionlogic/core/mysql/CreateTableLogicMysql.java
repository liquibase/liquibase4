package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.core.CreateTableAction;
import liquibase.action.core.SetColumnRemarksAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.CreateTableLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.item.core.PrimaryKey;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

import java.util.List;

public class CreateTableLogicMysql extends CreateTableLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(CreateTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);

        if (!errors.hasErrors()) {
            for (Column column : action.columns) {
                if (column.autoIncrementInformation != null) {
                    if (action.primaryKey == null) {
                        errors.addError("Auto-increment columns must have a primary key");
                    } else {
                        boolean foundKeyCol = false;
                        for (PrimaryKey.PrimaryKeyColumn pkCol : action.primaryKey.columns) {
                            if (pkCol.name.equals(column.name)) {
                                foundKeyCol = true;
                            }
                        }
                        if (!foundKeyCol) {
                            errors.addError("Auto-increment columns must be defined as a primary key");
                        }
                    }
                }
            }
        }

        return errors;
    }

    @Override
    public ActionResult execute(CreateTableAction action, Scope scope) throws ActionPerformException {
        DelegateResult result = (DelegateResult) super.execute(action, scope);

        for (Column column : CollectionUtil.createIfNull(action.columns)) {
            String columnRemarks = column.remarks;
            if (columnRemarks != null) {
                SetColumnRemarksAction remarksAction = new SetColumnRemarksAction();
                remarksAction.column = new ColumnReference(action.table.name, column.name);
                remarksAction.remarks = columnRemarks;
                result.addActions(remarksAction);
            }
        }

        return result;
    }

    @Override
    protected StringClauses generateSql(CreateTableAction action, Scope scope) {
        Database database = scope.getDatabase();

        StringClauses clauses = super.generateSql(action, scope);
        String remarks = action.table.remarks;
        if (remarks != null) {
            clauses.append("COMMENT=" + database.quoteString(remarks, scope));
        }

        return clauses;
    }

    @Override
    protected StringClauses generateColumnSql(Column column, CreateTableAction action, Scope scope, List<Action> additionalActions) {
        StringClauses clauses = super.generateColumnSql(column, action, scope, additionalActions);

        Database database = scope.getDatabase();
        String remarks = column.remarks;

        if (remarks != null) {
            clauses.append("COMMENT " + database.quoteString(remarks, scope));
        }


        return clauses;
    }
}

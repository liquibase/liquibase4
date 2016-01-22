package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateTableAction;
import liquibase.action.core.SetColumnRemarksAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.CreateTableLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

public class CreateTableLogicH2 extends CreateTableLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    protected boolean primaryKeyNamePreserved() {
        return false;
    }

    @Override
    public ValidationErrors validate(CreateTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);

        if (!errors.hasErrors()) {
            for (Column column : action.columns) {
                if (column.autoIncrementInformation != null) {
                    if (action.primaryKey != null) {
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
        DelegateResult result = new DelegateResult(action, null,
                new ExecuteSqlAction(generateSql(action, scope).toString()),
                action.table.remarks == null ? null : new ExecuteSqlAction(new StringClauses(" ")
                        .append("COMMENT ON TABLE")
                        .append(scope.getDatabase().escapeObjectName(action.table.toReference()))
                        .append("IS")
                        .append("'" + scope.getDatabase().escapeString(action.table.remarks) + "'"))
        );

        for (Column column : CollectionUtil.createIfNull(action.columns)) {
            String columnRemarks = column.remarks;
            if (columnRemarks != null) {
                SetColumnRemarksAction remarksAction = new SetColumnRemarksAction();
                remarksAction.columnName = new Column.ColumnReference(action.table.name, column.name);
                remarksAction.remarks = columnRemarks;
                result.addActions(remarksAction);
            }
        }

        return result;
    }
}

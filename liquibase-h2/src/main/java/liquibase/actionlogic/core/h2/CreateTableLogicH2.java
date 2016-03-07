package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateTableAction;
import liquibase.action.core.SetRemarksAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.CreateTableLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.item.core.PrimaryKey;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

public class CreateTableLogicH2 extends CreateTableLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public ValidationErrors validate(CreateTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);

        errors.removeUnsupportedField("primaryKey");
        errors.removeUnsupportedField("columns.autoIncrementInformation");

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
        DelegateResult result = new DelegateResult(action, null, new ExecuteSqlAction(generateSql(action, scope).toString()));

        if (action.table.remarks != null) {
            result.addActions(new SetRemarksAction(action.table.toReference(), action.table.remarks));
        }

        for (Column column : CollectionUtil.createIfNull(action.columns)) {
            if (column.remarks != null) {
                result.addActions(new SetRemarksAction(new ColumnReference(column.name, action.table.toReference()), column.remarks));
            }
        }

        return result;
    }
}

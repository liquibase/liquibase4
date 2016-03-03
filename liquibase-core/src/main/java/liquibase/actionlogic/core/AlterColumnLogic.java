package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.AlterColumnAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

public class AlterColumnLogic extends AbstractActionLogic<AlterColumnAction> {

    public enum Clauses {
        tableName,
        columnName,
        newDefinition,
    }

    @Override
    protected Class<AlterColumnAction> getSupportedAction() {
        return AlterColumnAction.class;
    }

    @Override
    public ValidationErrors validate(AlterColumnAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("column", "column.name",
                        "column.container", "column.container.name",
                        "newDefinition");
    }

    @Override
    public ActionResult execute(AlterColumnAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new ExecuteSqlAction(getAlterColumnClauses(action, scope)));
    }

    @Override
    public ActionStatus checkStatus(AlterColumnAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        result.assertCorrect(true, "Low-level action with no assertions to perform");

        return result;
    }

    protected StringClauses getAlterColumnClauses(AlterColumnAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses(" ")
                .append("ALTER TABLE")
                .append(Clauses.tableName, database.quoteObjectName(action.column.container, scope))
                .append(ObjectUtil.defaultIfNull(action.alterColumnKeyword, "ALTER COLUMN"))
                .append(Clauses.columnName, database.quoteObjectName(action.column.name, Column.class, scope))
                .append(Clauses.newDefinition, action.newDefinition.toString().trim());
    }
}

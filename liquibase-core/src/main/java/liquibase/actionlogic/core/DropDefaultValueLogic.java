package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.DropDefaultValueAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.util.StringClauses;

public class DropDefaultValueLogic extends AbstractSqlBuilderLogic<DropDefaultValueAction> {

    @Override
    protected Class<DropDefaultValueAction> getSupportedAction() {
        return DropDefaultValueAction.class;
    }

    @Override
    public ValidationErrors validate(DropDefaultValueAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("column", "column.container.name");
    }

    @Override
    public ActionResult execute(DropDefaultValueAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterColumnAction(
                action.column,
                generateSql(action, scope)));
    }

    @Override
    protected StringClauses generateSql(DropDefaultValueAction action, Scope scope) {
        return new StringClauses().append("DEFAULT NULL");
    }
}

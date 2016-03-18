package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.UpdateSqlAction;
import liquibase.action.core.DeleteDataAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.util.StringClauses;

public class DeleteDataLogic  extends AbstractActionLogic<DeleteDataAction> {

    public enum Clauses {
        columns,
        whereClause,
    }

    @Override
    protected Class<? extends DeleteDataAction> getSupportedAction() {
        return DeleteDataAction.class;
    }

    @Override
    public ValidationErrors validate(DeleteDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("relation", "relation.name");
    }

    @Override
    public ActionStatus checkStatus(DeleteDataAction action, Scope scope) {
        return new ActionStatus().assertCorrect(true, "Nothing to check");
    }

    @Override
    public ActionResult execute(DeleteDataAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new UpdateSqlAction(generateSql(action, scope)));
    }

    protected StringClauses generateSql(DeleteDataAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();

        StringClauses returnSql = new StringClauses()
                .append("DELETE")
                .append("FROM")
                .append(database.quoteObjectName(action.relation, scope));

        if (!action.where.isEmpty()) {
            returnSql.append("WHERE").append(Clauses.whereClause, action.where);
        }

        return returnSql;
    }
}
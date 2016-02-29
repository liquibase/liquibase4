package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropTableLogic extends AbstractActionLogic<DropTableAction> {

    @Override
    protected Class<DropTableAction> getSupportedAction() {
        return DropTableAction.class;
    }

    @Override
    public ValidationErrors validate(DropTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("table");

        if (!supportsDropTableCascadeConstraints()) {
            errors.checkUnsupportedFields("cascadeConstraints");
        }
        return errors;
    }

    protected boolean supportsDropTableCascadeConstraints() {
        return true;
    }

    @Override
    public ActionResult execute(DropTableAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

            actions.add(new ExecuteSqlAction(generateSql(action.table, action, scope)));

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(DatabaseObjectReference tableName, DropTableAction action, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses clauses = new StringClauses()
                .append("DROP TABLE")
                .append(database.quoteObjectName(tableName, scope));

        if (ObjectUtil.defaultIfNull(action.cascadeConstraints, false)) {
            clauses.append("CASCADE");
        }
        return clauses;
    }
}

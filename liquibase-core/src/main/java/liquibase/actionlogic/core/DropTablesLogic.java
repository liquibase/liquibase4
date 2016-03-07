package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropTablesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.core.RelationReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropTablesLogic extends AbstractActionLogic<DropTablesAction> {

    @Override
    protected Class<DropTablesAction> getSupportedAction() {
        return DropTablesAction.class;
    }

    @Override
    public ValidationErrors validate(DropTablesAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("tables", "tables.name");

        if (!supportsDropTableCascadeConstraints()) {
            errors.checkUnsupportedFields("cascadeConstraints");
        }
        return errors;
    }

    protected boolean supportsDropTableCascadeConstraints() {
        return true;
    }

    @Override
    public ActionResult execute(DropTablesAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (RelationReference table : action.tables) {
            actions.add(new ExecuteSqlAction(generateSql(table, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(DatabaseObjectReference tableName, DropTablesAction action, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses clauses = new StringClauses()
                .append("DROP TABLE")
                .append(database.quoteObjectName(tableName, scope));

        if (ObjectUtil.defaultIfNull(action.cascadeConstraints, false)) {
            clauses.append("CASCADE");
        }
        return clauses;
    }

    @Override
    public ActionStatus checkStatus(DropTablesAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (RelationReference table : action.tables) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(table, scope) == null, "Table "+table+" was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
        return status;

    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropForeignKeysAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.ForeignKey;
import liquibase.item.core.ForeignKeyReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropForeignKeysLogic extends AbstractActionLogic<DropForeignKeysAction> {

    @Override
    protected Class<DropForeignKeysAction> getSupportedAction() {
        return DropForeignKeysAction.class;
    }

    @Override
    public ValidationErrors validate(DropForeignKeysAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("foreignKeys", "foreignKeys.name", "foreignKeys.container");
    }

    @Override
    public ActionResult execute(DropForeignKeysAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();
        for (ForeignKeyReference foreignKey : CollectionUtil.createIfNull(action.foreignKeys)) {
            actions.add(new AlterTableAction(
                    foreignKey.getRelation(),
                    generateSql(foreignKey, action, scope)
            ));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    @Override
    public ActionStatus checkStatus(DropForeignKeysAction action, Scope scope) {
        ActionStatus actionStatus = new ActionStatus();
        try {
            for (ForeignKeyReference foreignKey : action.foreignKeys) {
                actionStatus.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(foreignKey, scope) == null, "Foreign key " + foreignKey + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return actionStatus.unknown(e);
        }
        return actionStatus;
    }

    protected StringClauses generateSql(ForeignKeyReference foreignKey, DropForeignKeysAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("DROP CONSTRAINT")
                .append(database.quoteObjectName(foreignKey.name, ForeignKey.class, scope));
    }
}

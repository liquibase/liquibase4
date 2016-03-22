package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.RenameSequenceAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Sequence;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class RenameSequenceLogic extends AbstractSqlBuilderLogic<RenameSequenceAction> {

    @Override
    protected Class<? extends RenameSequenceAction> getSupportedAction() {
        return RenameSequenceAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supports(Sequence.class, scope);
    }

    @Override
    protected StringClauses generateSql(RenameSequenceAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("RENAME")
                .append("SEQUENCE")
                .append(database.quoteObjectName(action.oldName, scope))
                .append("TO")
                .append(database.quoteObjectName(action.newName, scope));
    }

    @Override
    public ValidationErrors validate(RenameSequenceAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("oldName", "oldName.name")
                .checkRequiredFields("newName", "newName.name");
    }

    @Override
    public ActionStatus checkStatus(RenameSequenceAction action, Scope scope) {
        try {
            return new ActionStatus()
                    .assertCorrect(scope.getSingleton(SnapshotFactory.class).has(action.newName, scope), "New object name not found")
                    .assertCorrect(!scope.getSingleton(SnapshotFactory.class).has(action.oldName, scope), "Old object name found");
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }
}

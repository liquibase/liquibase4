package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexesAction;
import liquibase.action.core.DropSequencesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.IndexReference;
import liquibase.item.core.SequenceReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropSequencesLogic extends AbstractActionLogic<DropSequencesAction> {

    @Override
    protected Class<DropSequencesAction> getSupportedAction() {
        return DropSequencesAction.class;
    }

    @Override
    public ValidationErrors validate(DropSequencesAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("sequences", "sequences.name");
    }

    @Override
    public ActionStatus checkStatus(DropSequencesAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (SequenceReference sequence : action.sequences) {
                status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(sequence, scope) == null, "Sequence " + sequence + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
        return status;
    }

    @Override
    public ActionResult execute(DropSequencesAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (SequenceReference sequence : action.sequences) {
            actions.add(new ExecuteSqlAction(generateSql(sequence, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(SequenceReference sequence, DropSequencesAction action, Scope scope) {
        return new StringClauses()
                .append("DROP SEQUENCE")
                .append(scope.getDatabase().quoteObjectName(sequence, scope));
    }
}

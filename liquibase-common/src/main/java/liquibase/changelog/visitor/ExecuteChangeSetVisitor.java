package liquibase.changelog.visitor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CommitAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;
import liquibase.listener.ChangeSetListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes {@link Action}s in a changeSet and marks the changeSet as ran.
 */
public class ExecuteChangeSetVisitor implements ChangeSetVisitor {

    @Override
    public void visit(ChangeSet changeSet, Scope scope) throws LiquibaseException {
        ChangeLogHistoryService changeLogHistoryService = scope.get(Scope.Attr.changeLogHistoryService, ChangeLogHistoryService.class);
        if (changeLogHistoryService == null) {
            throw new LiquibaseException("No ChangeLogHistoryService configured");
        }

        Logger logger = LoggerFactory.getLogger(getClass());
        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);

        logger.debug("Running ChangeSet: "+changeSet.getIdentifier());

        try {
            fireWillRun(changeSet, scope);
            for (Action action : changeSet.actions) {
                actionExecutor.execute(action, scope);
            }

            fireActionsRan(changeSet, scope);

            changeLogHistoryService.setExecType(changeSet, ChangeSet.ExecType.EXECUTED, scope);

            actionExecutor.execute(new CommitAction(), scope);
            fireRan(changeSet, scope);
        } catch (LiquibaseException e) {
            fireRunFailed(e, changeSet, scope);
        }


        logger.debug("ChangeSet " + changeSet.getIdentifier() + " executed");

    }

    protected void fireActionsRan(ChangeSet changeSet, Scope scope) {
        for (ChangeSetListener listener : scope.getListeners(ChangeSetListener.class)) {
            listener.actionsRan(changeSet, scope);
        }

    }

    protected void fireRan(ChangeSet changeSet, Scope scope) {
        for (ChangeSetListener listener : scope.getListeners(ChangeSetListener.class)) {
            listener.ran(changeSet, scope);
        }

    }

    protected void fireRunFailed(LiquibaseException exception, ChangeSet changeSet, Scope scope) {
        for (ChangeSetListener listener : scope.getListeners(ChangeSetListener.class)) {
            listener.runFailed(exception, changeSet, scope);
        }
    }

    protected void fireWillRun(ChangeSet changeSet, Scope scope) {
        for (ChangeSetListener listener : scope.getListeners(ChangeSetListener.class)) {
            listener.willRun(changeSet, scope);
        }
    }
}

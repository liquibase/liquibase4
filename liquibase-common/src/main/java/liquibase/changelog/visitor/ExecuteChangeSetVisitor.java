package liquibase.changelog.visitor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.actionlogic.ActionExecutor;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;
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

        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);
        for (Action action : changeSet.actions) {
            actionExecutor.execute(action, scope);
        }

        changeLogHistoryService.setExecType(changeSet, ChangeSet.ExecType.EXECUTED, scope);


        LoggerFactory.getLogger(getClass()).debug("ChangeSet " + changeSet.getIdentifier()+" executed");

    }
}

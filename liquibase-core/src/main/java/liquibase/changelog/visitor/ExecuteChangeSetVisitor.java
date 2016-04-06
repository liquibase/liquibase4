package liquibase.changelog.visitor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.actionlogic.ActionExecutor;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;

/**
 * Executes {@link Action}s in a changeSet and marks the changeSet as ran.
 */
public class ExecuteChangeSetVisitor implements ChangeSetVisitor {

    @Override
    public void visit(ChangeSet changeSet, Scope scope) throws LiquibaseException {

        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);
        for (Action action : changeSet.actions) {
            System.out.println("Execute "+action);
            actionExecutor.execute(action, scope);
        }

        System.out.println("Success!");

    }
}

package liquibase.listener;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.actionlogic.ActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.exception.ActionPerformException;

public abstract class ActionListener implements LiquibaseListener {

    public void willRun(Action action, ActionLogic logic, Scope scope) {

    }

    public void ran(ActionResult result, Action action, ActionLogic logic, Scope scope) {

    }

    public void runFailed(ActionPerformException exception, Action action, ActionLogic logic, Scope scope) {

    }
}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.OutputMessageAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ExecuteResult;
import liquibase.exception.ActionPerformException;
import org.slf4j.LoggerFactory;

public class OutputMessageLogic extends AbstractActionLogic<OutputMessageAction> {

    @Override
    protected Class<? extends OutputMessageAction> getSupportedAction() {
        return OutputMessageAction.class;
    }

    @Override
    public boolean executeInteractsExternally(OutputMessageAction action, Scope scope) {
        return true;
    }

    @Override
    public ValidationErrors validate(OutputMessageAction action, Scope scope) {
        return super.validate(action, scope).checkRequiredFields("message");
    }

    @Override
    public ActionStatus checkStatus(OutputMessageAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }

    @Override
    public ActionResult execute(OutputMessageAction action, Scope scope) throws ActionPerformException {
        String target = action.target;
        if (target == null) {
            target = OutputMessageAction.MessageTarget.INFO.name();
        }
        target = target.toUpperCase();

        if (target.equals(OutputMessageAction.MessageTarget.STDOUT.name())) {
            System.out.println(action.message);
        } else if (target.equals(OutputMessageAction.MessageTarget.STDERR.name())) {
            System.err.println(action.message);
        } else if (target.equals(OutputMessageAction.MessageTarget.DEBUG.name())) {
            LoggerFactory.getLogger(getClass()).debug(action.message);
        } else if (target.equals(OutputMessageAction.MessageTarget.INFO.name())) {
            LoggerFactory.getLogger(getClass()).info(action.message);
        } else if (target.equals(OutputMessageAction.MessageTarget.WARN.name()) || target.equals("WARNING")) {
            LoggerFactory.getLogger(getClass()).warn(action.message);
        } else if (target.equals(OutputMessageAction.MessageTarget.ERROR.name()) || target.equals("FATAL") || target.equals("SEVERE")) {
            LoggerFactory.getLogger(getClass()).error(action.message);
        } else {
            throw new ActionPerformException("Unknown target: " + target);
        }

        return new ExecuteResult(action, "Output message");
    }


}

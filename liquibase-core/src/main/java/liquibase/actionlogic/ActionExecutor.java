package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.util.StringUtils;

import java.util.*;

/**
 * This class is used to execute {@link liquibase.action.Action} objects using the registered {@link liquibase.actionlogic.ActionLogic} implementations.
 */
public class ActionExecutor {

    private List<String> executedPlans = new ArrayList<>();

    /**
     * Executes an action.
     * Default implementation creates a {@link liquibase.actionlogic.ActionExecutor.Plan} for the given action and executes it.
     * Will return a single ActionResult if there is a single Action that is executed, but can return a {@link liquibase.actionlogic.CompoundResult} if multiple actions end up being executed to perform the starting action.
     */
    public ActionResult execute(Action action, Scope scope) throws ActionPerformException {
        Plan plan = createPlan(action, scope);
        if (plan.getValidationErrors().hasErrors()) {
            throw new ActionPerformException("Validation Error(s): "+ StringUtils.join(plan.getValidationErrors().getErrorMessages(), "; ")+" for "+action.describe());
        }
        executedPlans.add(plan.describe(true));
        return plan.execute(scope);
    }

    /**
     * Convenience version of {@link #execute(Action, Scope)} for performing a query
     */
    public QueryResult query(Action action, Scope scope) throws ActionPerformException {
        Plan plan = createPlan(action, scope);
        if (plan.getValidationErrors().hasErrors()) {
            throw new ActionPerformException(plan.getValidationErrors().toString());
        }

        executedPlans.add(plan.describe(true));
        return (QueryResult) plan.execute(scope);
    }

    public List<String> getExecutedPlans() {
        return Collections.unmodifiableList(executedPlans);
    }

    public void resetPlanHistory() {
        executedPlans.clear();
    }

    public ActionStatus checkStatus(Action action, Scope scope) {
        ActionLogic logic = getActionLogic(action, scope);

        if (logic == null) {
            return new ActionStatus().unknown("No ActionLogic implementation for "+action.describe()+" for "+scope.describe());
        }

        return logic.checkStatus(action, scope);
    }

    public ValidationErrors validate(Action action, Scope scope) {
       return createPlan(action, scope).getValidationErrors();
    }

    /**
     * Generates a Plan listing the Actions and corresponding ActionLogic implementations that will interact with external systems.
     * Normally {@link #execute(liquibase.action.Action, liquibase.Scope)} should be called, but this method is public for logging and testing purposes.
     * The Steps in the Plan will contain {@link liquibase.actionlogic.ActionLogic.InteractsExternally} implementations.
     */
    public Plan createPlan(Action action, Scope scope) {
        ValidationErrors errors = new ValidationErrors(action);
        Plan plan = new Plan(buildStep(0, action, errors, scope));
        plan.validationErrors = errors;
        return plan;
    }

    protected Plan.Step buildStep(int depth, Action action, ValidationErrors errors, Scope scope) {
        ActionLogic actionLogic = getActionLogic(action, scope);

        if (actionLogic == null) {
            errors.addError(": no supported ActionLogic implementation found for " + action.getClass().getName() + " '" + action.describe());
            return null;
        }

        ValidationErrors stepErrors = actionLogic.validate(action, scope);

        if (depth == 0) {
            errors.addAll(stepErrors, null);
        } else {
            for (String message : stepErrors.getErrorMessages()) {
                errors.addError(": Step error: "+message);
            }
            for (String message : stepErrors.getWarningMessages()) {
                errors.addWarning(": Step error: " + message);
            }
        }

        if (errors.hasErrors()) {
            return null;
        }

        if (actionLogic instanceof ActionLogic.InteractsExternally && ((ActionLogic.InteractsExternally) actionLogic).interactsExternally(action, scope)) {
            return new Plan.ActionStep(action, actionLogic);
        }

        ActionResult result;
        try {
            result = actionLogic.execute(action, scope);
        } catch (ActionPerformException e) {
            throw new UnexpectedLiquibaseException(e); //should not actually do anything since it doesn't interact externally
        }

        if (result instanceof DelegateResult) {
            List<Action> actions = ((DelegateResult) result).getActions();

            if (actions.size() == 0) {
                errors.addError(": "+ actionLogic.getClass().getName()+" tried to handle '"+action.describe()+"' but returned no actions to run");
                return null;
            } else {
                Plan.DelegateStep step = new Plan.DelegateStep(depth + 1, ((DelegateResult) result).getModifier());
                for (Action rewroteAction : actions) {
                    step.addStep(buildStep(depth + 1, rewroteAction, errors, scope));
                }
                return step;
            }
        } else {
            return new Plan.ActionStep(action, actionLogic);
        }
    }

    protected ActionLogic getActionLogic(Action action, Scope scope) {
        ActionLogicFactory actionLogicFactory = scope.getSingleton(ActionLogicFactory.class);

        return actionLogicFactory.getActionLogic(action, scope);
    }

    /**
     * An execution plan.
     */
    public static class Plan {

        private Step step;
        private ValidationErrors validationErrors = new ValidationErrors(null);

        public Plan(Step step) {
            this.step = step;
        }

        public Step getStep() {
            return step;
        }

        public ValidationErrors getValidationErrors() {
            return validationErrors;
        }

        public String describe(boolean includeLogicDescription) {
            if (step == null) {
                return "No steps";
            } else {
                return step.describe(includeLogicDescription);
            }
        }

        @Override
        public String toString() {
            return describe(true);
        }

        public ActionResult execute(Scope scope) throws ActionPerformException {
            if (step == null) {
                throw new ActionPerformException("No steps in action plan");
            } else {
                return step.execute(scope);
            }

        }

        public static abstract class Step {

            private ActionResult.Modifier modifier;

            public Step() {
            }

            public Step(ActionResult.Modifier modifier) {
                this.modifier = modifier;
            }

            public ActionResult.Modifier getModifier() {
                return modifier;
            }

            public abstract ActionResult execute(Scope scope) throws ActionPerformException;

            public abstract String describe(boolean includeLogicDescription);

            /**
             * If the passed result is a CompoundResult with just a single result, return just the nested result.
             */
            protected ActionResult flattenCompoundResult(ActionResult result) {
//                if (result instanceof CompoundResult && ((CompoundResult) result).getFlatResults().size() == 1) {
//                    return flattenCompoundResult(((CompoundResult) result).getFlatResults().get(0));
//                } else {
                    return result;
//                }
            }


        }

        /**
         * A step in a {@link liquibase.actionlogic.ActionExecutor.Plan}.
         * The step contains an Action to run, the ActionLogic to execute it, and a Deque of Modifiers to adjust the result.
         */
        public static class ActionStep extends Step {

            private Action action;
            private ActionLogic logic;

            public ActionStep(Action action, ActionLogic logic) {
                this.action = action;
                this.logic = logic;
            }

            public Action getAction() {
                return action;
            }

            public ActionLogic getLogic() {
                return logic;
            }

            @Override
            public String describe(boolean includeLogicDescription) {
                if (includeLogicDescription) {
                    return "Execute "+action.describe()+" with "+logic.getClass().getName();
                } else {
                    return action.describe();
                }
            }

            public ActionResult execute(Scope scope) throws ActionPerformException {
                ActionResult result = this.getLogic().execute(action, scope);
                if (this.getModifier() != null) {
                    result = getModifier().rewrite(flattenCompoundResult(result));
                }

                return flattenCompoundResult(result);
            }
        }

        public static class DelegateStep extends Step {
            private List<Step> steps = new ArrayList<>();
            int depth;

            public DelegateStep(int depth) {
                this.depth = depth;
            }

            public DelegateStep(int depth, ActionResult.Modifier modifier) {
                super(modifier);
                this.depth = depth;
            }

            public List<Step> getSteps() {
                return Collections.unmodifiableList(steps);
            }

            @Override
            public String describe(final boolean includeLogicDescription) {
                return StringUtils.pad(StringUtils.join(steps, "\n", new StringUtils.StringUtilsFormatter<Step>() {
                    @Override
                    public String toString(Step step) {
                        return step.describe(includeLogicDescription);
                    }
                }), depth*4);
            }


            @Override
            public ActionResult execute(Scope scope) throws ActionPerformException {
                CompoundResult result = new CompoundResult(null);
                for (Step step : getSteps()) {
                    result.addResult(step.execute(scope));
                }

                ActionResult returnResult;
                if (this.getModifier() == null) {
                    returnResult = result;
                } else {
                    returnResult = getModifier().rewrite(flattenCompoundResult(result));
                }

                return flattenCompoundResult(returnResult);
            }

            public void addStep(Step step) {
                this.steps.add(step);
            }
        }
    }

}

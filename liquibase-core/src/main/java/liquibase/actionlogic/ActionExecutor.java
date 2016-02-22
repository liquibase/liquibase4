package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to execute {@link liquibase.action.Action} objects using the registered {@link liquibase.actionlogic.ActionLogic} implementations.
 */
public class ActionExecutor implements SingletonObject {

    private List<String> executedPlans = new ArrayList<>();

    protected ActionExecutor() {
    }

    /**
     * Executes an action.
     * Default implementation calls {@link #createPlan(Action, Scope)} for the given action and executes the returned Plan.
     * Will return a single ActionResult if there is a single Action that is executed, but can return a {@link liquibase.actionlogic.CompoundResult} if multiple actions end up being executed to perform the starting action.
     * Throws {@link ActionPerformException} if there are validation errors.
     * Tracks all executed actions in {@link #getExecutedPlans()}
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
        return (QueryResult) execute(action, scope);
    }

    /**
     * Returns an unmodifiableList of all the plans executed by this ActionExector. For performance reasons, it only stores a description of the plan, not the original Plan object.
     * History can be reset with {@link #resetPlanHistory()}
     */
    public List<String> getExecutedPlans() {
        return Collections.unmodifiableList(executedPlans);
    }

    /**
     * Clears the history that would have been returned by {@link #getExecutedPlans()}
     */
    public ActionExecutor resetPlanHistory() {
        executedPlans.clear();
        return this;
    }

    /**
     * Returns the {@link ActionStatus} for whether the given action has been executed or not.
     */
    public ActionStatus checkStatus(Action action, Scope scope) {
        ActionLogic logic = getActionLogic(action, scope);

        if (logic == null) {
            return new ActionStatus().add(ActionStatus.Status.unknown, "No ActionLogic implementation for "+action.describe()+" for "+scope.describe());
        }

        return logic.checkStatus(action, scope);
    }

    /**
     * Returns the {@link ValidationErrors} for the given action.
     */
    public ValidationErrors validate(Action action, Scope scope) {
       return createPlan(action, scope).getValidationErrors();
    }

    /**
     * Generates a {@link liquibase.actionlogic.ActionExecutor.Plan} describing how the action will will interact with external systems.
     * Normally {@link #execute(liquibase.action.Action, liquibase.Scope)} should be called, but this method is public for logging and testing purposes.
     * The Steps in the Plan will contain implementations that return true for {@link ActionLogic#executeInteractsExternally(Action, Scope)}.
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

        if (actionLogic.executeInteractsExternally(action, scope)) {
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
                Plan.CompoundStep step = new Plan.CompoundStep(depth + 1, ((DelegateResult) result).getModifier());
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
     * Describes what {@link liquibase.actionlogic.ActionExecutor.Plan.Step}(s) will be executed and any validation errors
     */
    public static class Plan {

        private Step step;
        private ValidationErrors validationErrors = new ValidationErrors(null);

        public Plan(Step step) {
            this.step = step;
        }

        /**
         * Returns the root step to this plan.
         */
        public Step getStep() {
            return step;
        }

        /**
         * Returns the ValidationErrors object attached to this Plan.
         */
        public ValidationErrors getValidationErrors() {
            return validationErrors;
        }

        /**
         * Describes the plan, optionally including the ActionLogic class that executes each step.
         */
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

        /**
         * Executes the plan. Throws {@link ActionPerformException} if no steps are defined.
         */
        public ActionResult execute(Scope scope) throws ActionPerformException {
            if (step == null) {
                throw new ActionPerformException("No steps in action plan");
            } else {
                return step.execute(scope);
            }

        }

        /**
         * Base class for plan steps.
         * It can include an {@link DelegateResult.Modifier} to modify the result as part of the {@link #execute(Action, Scope)} method.
         */
        public static abstract class Step {

            private DelegateResult.Modifier modifier;

            public Step() {
            }

            public Step(DelegateResult.Modifier modifier) {
                this.modifier = modifier;
            }

            public DelegateResult.Modifier getModifier(Scope scope) {
                return modifier;
            }

            /**
             * The logic for executing this step. Called by {@link #execute(Scope)} before passing the result through the Modifier
             */
            protected abstract ActionResult executeImpl(Scope scope) throws ActionPerformException;

            /**
             * Describe the step. If longDescription is true, return a more vebose but complete description.
             */
            public abstract String describe(boolean longDescription);

            public final ActionResult execute(Scope scope) throws ActionPerformException {
                ActionResult result = executeImpl(scope);
                DelegateResult.Modifier modifier = this.getModifier(scope);
                if (modifier != null) {
                    result = modifier.rewrite(result);
                }

                return result;
            }
        }

        /**
         * A Step that executes an Action
         * The step contains the Action to run and the ActionLogic to execute it.
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
            public String describe(boolean longDescription) {
                if (longDescription) {
                    return "Execute "+action.describe()+" with "+logic.getClass().getName();
                } else {
                    return action.describe();
                }
            }

            protected ActionResult executeImpl(Scope scope) throws ActionPerformException {
                return this.getLogic().execute(action, scope);
            }
        }

        /**
         * A Step made up of multiple Steps. All will be executed and returned as a {@link CompoundResult}
         */
        public static class CompoundStep extends Step {
            private List<Step> steps = new ArrayList<>();
            int depth;

            public CompoundStep(int depth) {
                this.depth = depth;
            }

            public CompoundStep(int depth, DelegateResult.Modifier modifier) {
                super(modifier);
                this.depth = depth;
            }

            public List<Step> getSteps() {
                return Collections.unmodifiableList(steps);
            }

            @Override
            public String describe(final boolean longDescription) {
                return StringUtils.pad(StringUtils.join(steps, "\n", new StringUtils.StringUtilsFormatter<Step>() {
                    @Override
                    public String toString(Step step) {
                        return step.describe(longDescription);
                    }
                }), depth*4);
            }


            @Override
            protected ActionResult executeImpl(Scope scope) throws ActionPerformException {
                CompoundResult result = new CompoundResult(null);
                for (Step step : getSteps()) {
                    result.addResult(step.execute(scope));
                }

                return result;
            }

            public void addStep(Step step) {
                this.steps.add(step);
            }
        }
    }

}

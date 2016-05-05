package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.InsertDataFromQueryAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionExecutor;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;
import liquibase.util.Validate;

import java.util.List;

public class InsertDataFromQueryLogic extends AbstractSqlBuilderLogic<InsertDataFromQueryAction> {

    public enum Clauses {
        DESTINATION_COLUMNS,
        SELECT_QUERY
    }

    @Override
    protected Class<? extends InsertDataFromQueryAction> getSupportedAction() {
        return InsertDataFromQueryAction.class;
    }

    @Override
    public ValidationErrors validate(final InsertDataFromQueryAction action, final Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("destination", "destination.name")
                .checkRequiredFields("query")
                .checkField("query", new ValidationErrors.FieldCheck() {
                    @Override
                    public String check(Object obj) {
                        ValidationErrors queryErrors = scope.getSingleton(ActionExecutor.class).validate(action.query, scope);
                        if (queryErrors.hasErrors()) {
                            return queryErrors.toString();
                        }
                        return null;
                    }
                });
    }

    @Override
    public ActionStatus checkStatus(InsertDataFromQueryAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }

    @Override
    protected StringClauses generateSql(InsertDataFromQueryAction action, Scope scope) throws ActionPerformException {
        StringClauses columnsClause = new StringClauses("(", ", ", ")");
        for (String col : CollectionUtil.createIfNull(action.destinationColumns)) {
            columnsClause.append(scope.getDatabase().quoteObjectName(col, Column.class, scope));
        }

        return new StringClauses()
                .append("INSERT INTO")
                .append(scope.getDatabase().quoteObjectName(action.destination, scope))
                .append(Clauses.DESTINATION_COLUMNS, columnsClause)
                .append(Clauses.SELECT_QUERY, getSelectSql(action, scope));
    }

    protected StringClauses getSelectSql(InsertDataFromQueryAction action, Scope scope) throws ActionPerformException {
        try {
            ActionExecutor.Plan plan = scope.getSingleton(ActionExecutor.class).createPlan(action.query, scope);
            ActionExecutor.Plan.Step step = plan.getStep();
            if (step instanceof ActionExecutor.Plan.CompoundStep) {
                List<ActionExecutor.Plan.Step> steps = ((ActionExecutor.Plan.CompoundStep) step).getSteps();
                Validate.isTrue(steps.size() == 1, "Query returned " + steps.size() + " steps");
                step = steps.get(0);
            }

            Validate.isTrue(step instanceof ActionExecutor.Plan.ActionStep, "Unexpected step type: " + step.getClass().getName());

            Action queryAction = ((ActionExecutor.Plan.ActionStep) step).getAction();
            Validate.isTrue(queryAction instanceof QuerySqlAction, "Unexpected step action type: " + queryAction.getClass().getName());

            return ((QuerySqlAction) queryAction).sql;
        } catch (Validate.ValidationFailedException e) {
            throw new ActionPerformException(e);
        }
    }

}

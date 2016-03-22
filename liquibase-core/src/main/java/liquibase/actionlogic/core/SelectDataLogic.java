package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

public class SelectDataLogic extends AbstractSqlBuilderLogic<SelectDataAction> {

    public enum Clauses {
        columns,
        whereClauses,
        orderByColumns, distinct
    }

    @Override
    protected Class<? extends SelectDataAction> getSupportedAction() {
        return SelectDataAction.class;
    }

    @Override
    public ValidationErrors validate(SelectDataAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("relation", "relation.name",
                        "columns", "columns.name",
                        "order.name",
                        "joins.relation", "joins.relation.name", "joins.onClause"
                );
    }

    @Override
    public ActionResult execute(SelectDataAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new QuerySqlAction(generateSql(action, scope).toString()));
    }

    @Override
    public ActionStatus checkStatus(SelectDataAction action, Scope scope) {
        return new ActionStatus().nothingToCheck();
    }

    @Override
    protected StringClauses generateSql(SelectDataAction action, Scope scope) {
        StringClauses columns = new StringClauses(", ");
        for (SelectDataAction.SelectedColumn column : action.columns) {
            String columnString;
            if (column.virtual) {
                columnString = column.name;
            } else {
                columnString = scope.getDatabase().quoteObjectName(column.name, Column.class, scope);
                if (column.qualifier != null) {
                    columnString = column.qualifier + "." + columnString;
                }
            }
            if (column.alias != null) {
                columnString += " AS " + column.alias;
            }
            columns.append(columnString);
        }

        StringClauses returnSql = new StringClauses()
                .append("SELECT");
        if (ObjectUtil.defaultIfNull(action.distinct, false)) {
            returnSql.append(Clauses.distinct, "DISTINCT");
        }

        returnSql.append(Clauses.columns, columns)
                .append("FROM")
                .append(scope.getDatabase().quoteObjectName(action.relation, scope));
        if (action.relationAlias != null) {
            returnSql.append(action.relationAlias);
        }

        for (SelectDataAction.JoinedRelation join : action.joins) {
            String tableString = scope.getDatabase().quoteObjectName(join.relation, scope);
            StringClauses joinClause = new StringClauses();
            switch (join.joinType) {
                case leftOuter:
                    joinClause.append("LEFT OUTER");
                    break;
                case rightOuter:
                    joinClause.append("RIGHT OUTER");
                    break;
            }

            joinClause.append("JOIN").append(tableString);
            if (join.alias != null) {
                joinClause.append(join.alias);
            }
            if (!join.onClause.isEmpty()) {
                joinClause.append("ON").append(join.onClause.toString());
            }

            returnSql.append(joinClause.toString());
        }

        if (action.where != null && !action.where.isEmpty()) {
            returnSql.append("WHERE").append(Clauses.whereClauses, action.where);
        }

        if (action.order != null && action.order.size() > 0) {
            StringClauses orderClauses = new StringClauses(", ");
            for (SelectDataAction.OrderedByColumn column : action.order) {
                String columnString = scope.getDatabase().quoteObjectName(column.name, Column.class, scope);
                if (column.qualifier != null) {
                    columnString = column.qualifier + "." + columnString;
                }
                if (column.direction != null) {
                    columnString += " "+column.direction.name();
                }

                orderClauses.append(columnString);
            }
            returnSql.append("ORDER BY").append(Clauses.orderByColumns, orderClauses);
        }

        return returnSql;
    }

}

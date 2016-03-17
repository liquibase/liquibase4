package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.AlterNullableAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObject;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class AlterNullableLogic extends AbstractActionLogic<AlterNullableAction> {

    @Override
    protected Class<? extends AlterNullableAction> getSupportedAction() {
        return AlterNullableAction.class;
    }

    @Override
    public ValidationErrors validate(final AlterNullableAction action, Scope scope) {
        ValidationErrors validationErrors = super.validate(action, scope)
                .checkRequiredFields("nullable")
                .checkRequiredFields("column", "column.container", "column.name")
                .checkField("valueForExistingNulls", new ValidationErrors.FieldCheck() {
                    @Override
                    public String check(Object obj) {
                        if (action.nullable) {
                            return "only valid when nullable=false";
                        }
                        return null;
                    }
                });

        if (!scope.getDatabase().supports(Database.Feature.NAMED_NOT_NULL_CONSTRAINTS, scope)) {
            validationErrors.checkUnsupportedFields("constraintName");
        }
        return validationErrors;
    }

    @Override
    public ActionStatus checkStatus(AlterNullableAction action, Scope scope) {
        try {
            ActionStatus status = new ActionStatus();
            Column snapshotColumn = scope.getSingleton(SnapshotFactory.class).snapshot(action.column, scope);
            status.assertPropertyCorrect(action, snapshotColumn, "nullable");

            return status;
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(AlterNullableAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, createAction(action, scope));
    }

    protected Action createAction(AlterNullableAction action, Scope scope) {
        if (action.nullable) {
            if (action.constraintName == null) {
                return new AlterColumnAction(action.column, new StringClauses().append("NULL"));
            } else {
                return new AlterTableAction(action.column.getRelation(), new StringClauses().append("DROP").append("CONSTRAINT").append(scope.getDatabase().quoteObjectName(action.constraintName, DatabaseObject.class, scope)));
            }
        } else {
            if (action.constraintName == null) {
                return new AlterColumnAction(action.column, new StringClauses().append("NOT NULL"));
            } else {
                return new AlterTableAction(action.column.getRelation(), new StringClauses()
                        .append("ADD")
                        .append("CONSTRAINT")
                        .append(scope.getDatabase().quoteObjectName(action.constraintName, DatabaseObject.class, scope))
                        .append("NOT NULL")
                );
            }
        }
    }

}

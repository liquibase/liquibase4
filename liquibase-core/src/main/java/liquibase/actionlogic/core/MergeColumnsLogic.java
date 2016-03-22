package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AddColumnsAction;
import liquibase.action.core.DropColumnsAction;
import liquibase.action.core.MergeColumnsAction;
import liquibase.action.core.UpdateDataAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.FunctionCall;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.snapshot.SnapshotFactory;

import java.util.Collection;

public class MergeColumnsLogic extends AbstractActionLogic<MergeColumnsAction> {

    @Override
    protected Class<? extends MergeColumnsAction> getSupportedAction() {
        return MergeColumnsAction.class;
    }

    @Override
    public ValidationErrors validate(MergeColumnsAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("relation", "relation.name", "column1Name", "column2Name", "finalColumnName", "finalColumnType");
    }

    @Override
    public ActionStatus checkStatus(MergeColumnsAction action, Scope scope) {
        try {
            SnapshotFactory snapshotFactory = scope.getSingleton(SnapshotFactory.class);
            ActionStatus status = new ActionStatus();
            status.assertApplied(!snapshotFactory.has(new ColumnReference(action.column1Name, action.relation), scope), action.relation + " still has column " + action.column1Name);
            status.assertApplied(!snapshotFactory.has(new ColumnReference(action.column2Name, action.relation), scope), action.relation + " still has column " + action.column2Name);
            status.assertApplied(snapshotFactory.has(new ColumnReference(action.finalColumnName, action.relation), scope), action.relation + " still has column " + action.finalColumnName);

            return status;
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    public ActionResult execute(MergeColumnsAction action, Scope scope) throws ActionPerformException {

        String joinString = action.joinString;
        if (joinString == null) {
            joinString = " ";
        }

        return new DelegateResult(action, null,
                new AddColumnsAction(new Column(action.finalColumnName, action.relation, action.finalColumnType, true)),
                new UpdateDataAction(action.relation, null, new UpdateDataAction.UpdatedColumn(action.finalColumnName, getConcatValue(action.column1Name, joinString, action.column2Name, action, scope))),
                new DropColumnsAction(
                        new ColumnReference(action.column1Name, action.relation),
                        new ColumnReference(action.column2Name, action.relation)
                )
        );
    }

    protected Object getConcatValue(String column1Name, String joinString, String column2Name, MergeColumnsAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new FunctionCall(database.quoteObjectName(column1Name, Column.class, scope) + " || " + database.quoteString(joinString, scope) + " || " + database.quoteObjectName(column2Name, Column.class, scope));
    }
}

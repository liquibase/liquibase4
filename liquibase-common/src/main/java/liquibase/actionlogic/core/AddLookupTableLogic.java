package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.*;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Column;
import liquibase.item.core.ForeignKey;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.Table;
import liquibase.snapshot.SnapshotFactory;

import java.util.Collections;

public class AddLookupTableLogic extends AbstractActionLogic<AddLookupTableAction> {
    @Override
    protected Class<? extends AddLookupTableAction> getSupportedAction() {
        return AddLookupTableAction.class;
    }

    @Override
    public ValidationErrors validate(AddLookupTableAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("existingColumn", "existingColumn.name", "existingColumn.container")
                .checkRequiredFields("newColumn", "newColumn.name", "newColumn.container");
    }

    @Override
    public ActionStatus checkStatus(AddLookupTableAction action, Scope scope) {
        ActionStatus status = new ActionStatus();

        try {
            SnapshotFactory snapshotFactory = scope.getSingleton(SnapshotFactory.class);
            status.assertCorrect(snapshotFactory.has(action.newColumn, scope), "New table and/or column not created");

            return status;
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }
    }

    @Override
    public ActionResult execute(AddLookupTableAction action, Scope scope) throws ActionPerformException {
        DelegateResult delegateResult = new DelegateResult(action, null);
        SelectDataAction selectDataAction = new SelectDataAction(action.existingColumn.getRelation(), new SelectDataAction.SelectedColumn(null, action.existingColumn.name, null));
        selectDataAction.distinct = true;

        delegateResult.addActions(
                new CreateTableAction(new Table(action.newColumn.getRelation().name, action.newColumn.getRelation().getSchema()))
                        .addColumn(new Column(action.newColumn.name, action.newColumn.getRelation(), action.newColumnDataType, false))
                        .setPrimaryKey(new PrimaryKey(action.primaryKeyName, action.newColumn.getRelation(), action.newColumn.name)),
                new InsertDataFromQueryAction(action.newColumn.getRelation(), selectDataAction),
                new AddForeignKeysAction(new ForeignKey(action.foreignKeyName, action.existingColumn.getRelation(), action.newColumn.getRelation(), Collections.singletonList(action.existingColumn.name), Collections.singletonList(action.newColumn.name)))
        );

        return delegateResult;
    }
}

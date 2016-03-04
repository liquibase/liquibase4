package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.RenameTableAction;
import liquibase.action.core.RenameViewAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.ItemReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class RenameViewLogic extends AbstractSqlBuilderLogic<RenameViewAction> {

    @Override
    protected Class<RenameViewAction> getSupportedAction() {
        return RenameViewAction.class;
    }

    @Override
    public ValidationErrors validate(final RenameViewAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("oldName", "oldName.name", "oldName.type",
                        "newName", "newName.name", "newName.type")
                .checkField("newName", new ValidationErrors.FieldCheck<ItemReference>() {
                    @Override
                    public String check(ItemReference obj) {
                        if (obj.name.equals(action.oldName.name)) {
                            return "Cannot rename to the same name";
                        }

                        if (obj.container != null && action.oldName.container == null) {
                            return "Cannot rename to a different "+ obj.container.type.getSimpleName();
                        }
                        if (obj.container != null && !obj.container.equals(action.oldName.container, true)) {
                            return "Cannot rename to a different "+ obj.container.type.getSimpleName();
                        }
                        return null;
                    }
                });
    }



    @Override
    protected StringClauses generateSql(RenameViewAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("RENAME")
                .append("VIEW")
                .append(database.quoteObjectName(action.oldName, scope))
                .append("TO")
                .append(database.quoteObjectName(action.newName, scope));
    }

    @Override
    public ActionStatus checkStatus(RenameViewAction action, Scope scope) {
        try {
            return new ActionStatus().assertCorrect(scope.getSingleton(SnapshotFactory.class).has(action.newName, scope), "Object not renamed");
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }
}

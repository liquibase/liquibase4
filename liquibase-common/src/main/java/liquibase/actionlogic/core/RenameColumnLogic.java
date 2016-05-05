package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.RenameColumnAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.ItemReference;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

public class RenameColumnLogic extends AbstractSqlBuilderLogic<RenameColumnAction> {

    @Override
    protected Class<RenameColumnAction> getSupportedAction() {
        return RenameColumnAction.class;
    }

    @Override
    public ValidationErrors validate(final RenameColumnAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("relation", "relation.name",
                        "oldName", "newName")
                .checkField("newName", new ValidationErrors.FieldCheck<String>() {
                    @Override
                    public String check(String newName) {
                        if (newName.equals(action.oldName)) {
                            return "cannot rename to the same name";
                        }
                        return null;
                    }
                });
    }


    @Override
    protected StringClauses generateSql(RenameColumnAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("ALTER TABLE")
                .append(database.quoteObjectName(action.relation, scope))
                .append("RENAME")
                .append("COLUMN")
                .append(database.quoteObjectName(action.oldName, Column.class, scope))
                .append("TO")
                .append(database.quoteObjectName(action.newName, Column.class, scope));
    }

    @Override
    public ActionStatus checkStatus(RenameColumnAction action, Scope scope) {
        try {
            return new ActionStatus().assertCorrect(scope.getSingleton(SnapshotFactory.class).has(new ColumnReference(action.newName, action.relation), scope), "Column not renamed");
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }
}

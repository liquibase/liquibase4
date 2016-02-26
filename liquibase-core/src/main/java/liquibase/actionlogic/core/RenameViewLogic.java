package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.RenameViewAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.item.core.View;
import liquibase.util.StringClauses;

public class RenameViewLogic extends AbstractSqlBuilderLogic<RenameViewAction> {

    @Override
    protected Class<RenameViewAction> getSupportedAction() {
        return RenameViewAction.class;
    }

    @Override
    public ValidationErrors validate(RenameViewAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("oldViewName", "newViewName");
    }

    @Override
    protected StringClauses generateSql(RenameViewAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("RENAME")
                .append(database.quoteObjectName(action.oldViewName, scope))
                .append("TO")
                .append(database.quoteObjectName(action.newViewName.name, View.class, scope));
    }
}

package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.core.RenameColumnAction;
import liquibase.actionlogic.core.RenameColumnLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.item.core.Column;
import liquibase.util.StringClauses;

public class RenameColumnLogicH2 extends RenameColumnLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected StringClauses generateSql(RenameColumnAction action, Scope scope) {
        return new StringClauses()
                .append("ALTER TABLE")
                .append(scope.getDatabase().quoteObjectName(action.relation, scope))
                .append("ALTER COLUMN")
                .append(scope.getDatabase().quoteObjectName(action.oldName, Column.class, scope))
                .append("RENAME TO")
                .append(scope.getDatabase().quoteObjectName(action.newName, Column.class, scope));
    }
}

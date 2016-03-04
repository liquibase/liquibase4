package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.RenameTableAction;
import liquibase.action.core.RenameViewAction;
import liquibase.actionlogic.core.RenameTableLogic;
import liquibase.actionlogic.core.RenameViewLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.util.StringClauses;

public class RenameViewLogicH2 extends RenameViewLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected StringClauses generateSql(RenameViewAction action, Scope scope) {
        return new StringClauses()
                .append("ALTER TABLE")
                .append(scope.getDatabase().quoteObjectName(action.oldName, scope))
                .append("RENAME TO")
                .append(scope.getDatabase().quoteObjectName(action.newName, scope));
    }
}

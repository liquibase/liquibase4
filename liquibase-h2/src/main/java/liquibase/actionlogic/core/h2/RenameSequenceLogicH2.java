package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.RenameSequenceAction;
import liquibase.actionlogic.core.RenameSequenceLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;

public class RenameSequenceLogicH2 extends RenameSequenceLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public ValidationErrors validate(RenameSequenceAction action, Scope scope) {
        return new ValidationErrors(action).addUnsupportedError("action");
    }
}

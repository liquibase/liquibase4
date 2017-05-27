package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.AddAutoIncrementAction;
import liquibase.actionlogic.core.AddAutoIncrementLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;

public class AddAutoIncrementLogicH2 extends AddAutoIncrementLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public ValidationErrors validate(AddAutoIncrementAction action, Scope scope) {
        return super.validate(action, scope)
                .checkUnsupportedFields("autoIncrementDetails.incrementBy")
                .checkRequiredFields("dataType");
    }
}

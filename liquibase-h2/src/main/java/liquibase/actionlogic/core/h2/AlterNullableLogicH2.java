package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.AlterNullableAction;
import liquibase.actionlogic.core.AlterNullableLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;

public class AlterNullableLogicH2 extends AlterNullableLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createAction(AlterNullableAction action, Scope scope) {
        AlterColumnAction createdAction = ((AlterColumnAction) super.createAction(action, scope));

        if (action.nullable) {
            createdAction.newDefinition.replace("NULL", "SET NULL");
        } else {
            createdAction.newDefinition.replace("NOT NULL", "SET NOT NULL");
        }

        return createdAction;
    }
}

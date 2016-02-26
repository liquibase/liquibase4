package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.AddForeignKeysAction;
import liquibase.actionlogic.core.AddForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.ForeignKey;

public class AddForeignKeysLogicMysql extends AddForeignKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(AddForeignKeysAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);
        for (ForeignKey fk : action.foreignKeys) {
            if (fk == null) {
                continue;
            }

            if (fk.updateRule == ForeignKey.ReferentialAction.setDefault) {
                errors.addUnsupportedError("update rule SET DEFAULT");
            }

            if (fk.deleteRule == ForeignKey.ReferentialAction.setDefault) {
                errors.addUnsupportedError("delete rule SET DEFAULT");
            }
        }
        return errors;
    }
}

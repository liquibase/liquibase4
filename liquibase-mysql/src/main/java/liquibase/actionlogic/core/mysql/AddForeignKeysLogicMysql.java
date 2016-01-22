package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.AddForeignKeysAction;
import liquibase.actionlogic.core.AddForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.ForeignKey;

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

            if (fk.updateRule == ForeignKey.ConstraintType.importedKeySetDefault) {
                errors.addUnsupportedError("update rule SET DEFAULT", action);
            }

            if (fk.deleteRule == ForeignKey.ConstraintType.importedKeySetDefault) {
                errors.addUnsupportedError("delete rule SET DEFAULT", action);
            }
        }
        return errors;
    }
}

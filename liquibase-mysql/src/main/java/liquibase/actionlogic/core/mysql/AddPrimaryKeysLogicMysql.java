package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.AddPrimaryKeysAction;
import liquibase.actionlogic.core.AddPrimaryKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.PrimaryKey;

public class AddPrimaryKeysLogicMysql extends AddPrimaryKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(AddPrimaryKeysAction action, Scope scope) {
        ValidationErrors validate = super.validate(action, scope);

        for (PrimaryKey pk : action.primaryKeys) {
            validate.checkUnsupportedFields(pk, "name");
                for (PrimaryKey.PrimaryKeyColumn col : pk.columns) {
                    if (col.descending != null && col.descending) {
                        validate.addError("Cannot specify descending primary keys on "+scope.getDatabase().getShortName());
                    }
            }

        }

        return validate;
    }
}

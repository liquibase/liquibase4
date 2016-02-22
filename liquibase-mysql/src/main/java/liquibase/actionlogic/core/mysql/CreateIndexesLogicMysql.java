package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.CreateIndexesAction;
import liquibase.actionlogic.core.CreateIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.ValidationErrors;

public class CreateIndexesLogicMysql extends CreateIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(CreateIndexesAction action, Scope scope) {
        ValidationErrors validate = super.validate(action, scope)
                .checkRequiredFields("indexes.name");
        return validate;
    }
}

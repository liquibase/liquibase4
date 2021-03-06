package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.CreateIndexesAction;
import liquibase.actionlogic.core.CreateIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;

public class CreateIndexesLogicMysql extends CreateIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(CreateIndexesAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("indexes.name");
    }
}

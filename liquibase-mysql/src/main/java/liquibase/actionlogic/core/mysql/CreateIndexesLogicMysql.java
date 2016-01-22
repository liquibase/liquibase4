package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.CreateIndexesAction;
import liquibase.actionlogic.core.CreateIndexesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.Index;

public class CreateIndexesLogicMysql extends CreateIndexesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(CreateIndexesAction action, Scope scope) {
        ValidationErrors validate = super.validate(action, scope);
        for (Index index : action.indexes) {
            validate.checkRequiredFields(index, "name");
            for (Index.IndexedColumn col : index.columns) {
                if (col.descending != null && col.descending) {
                    validate.addError("Cannot specify descending indexes on "+scope.getDatabase().getShortName());
                }
            }
        }
        return validate;
    }
}

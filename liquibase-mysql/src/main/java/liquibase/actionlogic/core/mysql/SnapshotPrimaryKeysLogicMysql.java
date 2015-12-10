package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.core.SnapshotPrimaryKeysLogicJdbc;
import liquibase.database.Database;
import liquibase.database.core.mysql.MySQLDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.PrimaryKey;

public class SnapshotPrimaryKeysLogicMysql extends SnapshotPrimaryKeysLogicJdbc {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MySQLDatabase.class;
    }

    @Override
    public ValidationErrors validate(Action action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
            if (relatedTo.instanceOf(PrimaryKey.class) && relatedTo.name != null) {
                errors.addError("Mysql does not support primary key names");
                break;
            }
        }
        return errors;
    }
}

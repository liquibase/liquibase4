package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.core.SnapshotTablesLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;

public class SnapshotTablesLogicMysql extends SnapshotTablesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        Action snapshotAction = super.createSnapshotAction(relatedTo, action, scope);
//        Object tableArg = ((QueryJdbcMetaDataAction) snapshotAction).arguments.get(2);
//        if (tableArg != null) {
//            ((QueryJdbcMetaDataAction) snapshotAction).arguments.set(2, ((MysqlDatabase) scope.getDatabase()).escapeStringForLike((String) tableArg));
//        }
        return snapshotAction;
    }
}

package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.core.SnapshotViewsLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;

public class SnapshotViewsLogicH2 extends SnapshotViewsLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        return ((SelectDataAction) super.createSnapshotAction(relatedTo, action, scope))
                .addColumn(new SelectDataAction.SelectedColumn("REMARKS"));
    }
}

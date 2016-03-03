package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.core.SnapshotSequencesLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;

public class SnapshotSequencesLogicH2 extends SnapshotSequencesLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(String catalogName, String schemaName, String sequenceName, Scope scope) {
        SelectDataAction select = (SelectDataAction) super.createSnapshotAction(catalogName, schemaName, sequenceName, scope);

        select.removeColumn(new SelectDataAction.SelectedColumn("START_VALUE"))
                .replaceColumn(new SelectDataAction.SelectedColumn("CACHE_SIZE"), new SelectDataAction.SelectedColumn(null, "CACHE", "CACHE_SIZE"))
                .addWhere("IS_GENERATED=FALSE");

        return select;
    }
}

package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.actionlogic.core.SnapshotUniqueConstraintsLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.core.UniqueConstraint;
import org.apache.velocity.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class SnapshotUniqueConstraintsLogicH2 extends SnapshotUniqueConstraintsLogic {


    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        SelectDataAction snapshotAction = (SelectDataAction) super.createSnapshotAction(relatedTo, action, scope);
        snapshotAction.columns = new ArrayList<>(Arrays.asList(
                new SelectDataAction.SelectedColumn("TC", "CONSTRAINT_NAME", null),
                new SelectDataAction.SelectedColumn("TC", "TABLE_CATALOG", null),
                new SelectDataAction.SelectedColumn("TC", "TABLE_SCHEMA", null),
                new SelectDataAction.SelectedColumn("TC", "TABLE_NAME", null),
                new SelectDataAction.SelectedColumn(null, "FALSE", "IS_DEFERRABLE", true),
                new SelectDataAction.SelectedColumn(null, "FALSE", "INITIALLY_DEFERRED", true),
                new SelectDataAction.SelectedColumn("COLUMN_LIST")));
        snapshotAction.relation.name = "CONSTRAINTS";
        snapshotAction.joins.clear();
        snapshotAction.order.clear();

        return snapshotAction;
    }

    @Override
    protected UniqueConstraint convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        UniqueConstraint returnObject = super.convertToObject(object, relatedTo, originalAction, scope);

        returnObject.columns = Arrays.asList(StringUtils.split(((RowBasedQueryResult.Row) object).get("COLUMN_LIST", String.class), ","));

        return returnObject;
    }

    protected DelegateResult.Modifier createModifier(DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, final Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope);
    }
}

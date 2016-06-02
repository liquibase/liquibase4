package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotItemsLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.CheckConstraint;
import liquibase.item.core.RowData;

public class SnapshotRowDataLogic extends AbstractSnapshotItemsLogic<SnapshotItemsAction, RowData> {

    @Override
    protected Class<RowData> getTypeToSnapshot() {
        return RowData.class;
    }

    @Override
    public int getPriority(SnapshotItemsAction action, Scope scope) {
        if (action.typeToSnapshot.isAssignableFrom(RowData.class)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return null;
    }

    @Override
    public ActionResult execute(ItemReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        return new ObjectBasedQueryResult(action, null);
    }
}

package liquibase.actionlogic;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.database.MockJdbcConnection;
import liquibase.database.core.MockDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.ItemReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MockSnapshotLogic extends AbstractActionLogic<SnapshotItemsAction> {

    @Override
    protected boolean supportsScope(Scope scope) {
        return scope.getDatabase() instanceof MockDatabase
                && scope.getDatabase().getConnection() instanceof MockJdbcConnection
                && ((MockJdbcConnection) scope.getDatabase().getConnection()).snapshot != null;
    }

    @Override
    protected Class<? extends SnapshotItemsAction> getSupportedAction() {
        return SnapshotItemsAction.class;
    }

    @Override
    public ActionResult execute(SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        List<Item> items = new ArrayList<>();
        for (ItemReference reference : action.relatedTo) {
            items.addAll(((MockJdbcConnection) scope.getDatabase().getConnection()).snapshot.getAll(action.typeToSnapshot, reference));
        }
        return new ObjectBasedQueryResult(action, items);
    }
}

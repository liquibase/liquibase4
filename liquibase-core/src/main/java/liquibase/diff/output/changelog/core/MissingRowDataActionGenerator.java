package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateIndexesAction;
import liquibase.action.core.InsertDataAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.core.Index;
import liquibase.item.core.RowData;
import liquibase.item.core.Table;
import liquibase.snapshot.Snapshot;

import java.util.ArrayList;
import java.util.List;

public class MissingRowDataActionGenerator implements MissingObjectActionGenerator<RowData> {


    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (RowData.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends Item>[] runAfterTypes() {
        return new Class[] {
                Table.class,
                Column.class,
        };
    }

    @Override
    public Class<? extends Item>[] runBeforeTypes() {
        return null;
    }

    @Override
    public List<? extends Action> fixMissing(RowData missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        ArrayList<InsertDataAction> actions = new ArrayList<>();
        actions.add(new InsertDataAction(missingObject));

        return actions;
    }
}

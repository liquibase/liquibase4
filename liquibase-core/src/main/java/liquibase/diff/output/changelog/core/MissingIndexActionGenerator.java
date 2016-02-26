package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateIndexesAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.core.Index;
import liquibase.item.core.Table;

import java.util.ArrayList;
import java.util.List;

public class MissingIndexActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (Index.class.isAssignableFrom(objectType)) {
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
    public List<? extends Action> fixMissing(Item missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        Index index = (Index) missingObject;

        ArrayList<CreateIndexesAction> actions = new ArrayList<>();
        actions.add(new CreateIndexesAction(index));

        return actions;
    }
}

package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AddPrimaryKeysAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.core.Index;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.Table;

import java.util.ArrayList;
import java.util.List;

public class MissingPrimaryKeyActionGenerator implements MissingObjectActionGenerator<PrimaryKey> {


    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (PrimaryKey.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends Item>[] runAfterTypes() {
        return new Class[] {
                Table.class,
                Column.class
        };

    }

    @Override
    public Class<? extends Item>[] runBeforeTypes() {
        return new Class[] {
                Index.class
        };
    }

    @Override
    public List<? extends Action> fixMissing(PrimaryKey missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {

        ArrayList<AddPrimaryKeysAction> actions = new ArrayList<>();
        actions.add(new AddPrimaryKeysAction(missingObject));

        return actions;
    }
}

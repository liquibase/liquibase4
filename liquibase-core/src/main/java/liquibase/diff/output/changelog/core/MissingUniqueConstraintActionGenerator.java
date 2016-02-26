package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AddUniqueConstraintsAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.core.Index;
import liquibase.item.core.Table;
import liquibase.item.core.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

public class MissingUniqueConstraintActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (UniqueConstraint.class.isAssignableFrom(objectType)) {
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
    public List<? extends Action> fixMissing(Item missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        UniqueConstraint uq = (UniqueConstraint) missingObject;

        ArrayList<AddUniqueConstraintsAction> actions = new ArrayList<>();
        actions.add(new AddUniqueConstraintsAction(uq));

        return actions;
    }
}

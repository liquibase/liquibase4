package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AddForeignKeysAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;
import liquibase.item.core.*;

import java.util.ArrayList;
import java.util.List;

public class MissingForeignKeyActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (ForeignKey.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends Item>[] runAfterTypes() {
        return new Class[] {
                Table.class,
                Column.class,
                PrimaryKey.class,
                UniqueConstraint.class,
                Index.class
        };
    }

    @Override
    public Class<? extends Item>[] runBeforeTypes() {
        return null;
    }

    @Override
    public List<? extends Action> fixMissing(Item missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        ForeignKey fk = (ForeignKey) missingObject;

        ArrayList<AddForeignKeysAction> actions = new ArrayList<>();
        actions.add(new AddForeignKeysAction(fk));

        return actions;
    }
}

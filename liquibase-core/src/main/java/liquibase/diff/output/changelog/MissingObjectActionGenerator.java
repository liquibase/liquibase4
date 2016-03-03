package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;

import java.util.List;

public interface MissingObjectActionGenerator<ItemType extends Item> extends ActionGenerator {

    List<? extends Action> fixMissing(ItemType missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);
}

package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;

import java.util.List;

public interface MissingObjectActionGenerator extends ActionGenerator {

    List<? extends Action> fixMissing(Item missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);
}

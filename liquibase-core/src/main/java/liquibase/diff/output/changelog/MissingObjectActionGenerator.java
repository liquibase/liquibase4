package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.snapshot.Snapshot;
import liquibase.structure.DatabaseObject;

import java.util.List;

public interface MissingObjectActionGenerator extends ActionGenerator {

    public List<? extends Action> fixMissing(DatabaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);
}

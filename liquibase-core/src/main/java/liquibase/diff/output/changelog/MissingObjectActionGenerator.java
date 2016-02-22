package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;

import java.util.List;

public interface MissingObjectActionGenerator extends ActionGenerator {

    List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);
}

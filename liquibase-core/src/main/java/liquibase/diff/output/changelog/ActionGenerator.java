package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.servicelocator.Service;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;

public interface ActionGenerator extends Service {

    int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);

    public Class<? extends LiquibaseObject>[] runAfterTypes();

    public Class<? extends LiquibaseObject>[] runBeforeTypes();

}

package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.servicelocator.Service;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;

/**
 * Base interface for all classes that generate actions for missing/unexpected/changed objects.
 * Includes {@link #runAfterTypes()} and {@link #runBeforeTypes()} for controlling the order that implementations are ran in.
 *
 * @see MissingObjectActionGenerator
 */
public interface ActionGenerator extends Service {

    int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);

    /**
     * Returns the classes which should be handled by other ActionGenerators before this implementation runs.
     * Return null if there are no prerequisites.
     */
    Class<? extends LiquibaseObject>[] runAfterTypes();

    /**
     * Returns the classes which should be handled by other ActionGenerators after this implementation runs.
     * Return null if any can be ran after this.
     */
    Class<? extends LiquibaseObject>[] runBeforeTypes();

}

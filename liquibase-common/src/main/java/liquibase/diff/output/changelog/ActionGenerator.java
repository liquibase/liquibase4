package liquibase.diff.output.changelog;

import liquibase.Scope;
import liquibase.plugin.Plugin;
import liquibase.snapshot.Snapshot;
import liquibase.item.Item;

/**
 * Base interface for all classes that generate actions for missing/unexpected/changed objects.
 * Includes {@link #runAfterTypes()} and {@link #runBeforeTypes()} for controlling the order that implementations are ran in.
 *
 * @see MissingObjectActionGenerator
 */
public interface ActionGenerator extends Plugin {

    int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope);

    /**
     * Returns the classes which should be handled by other ActionGenerators before this implementation runs.
     * Return null if there are no prerequisites.
     */
    Class<? extends Item>[] runAfterTypes();

    /**
     * Returns the classes which should be handled by other ActionGenerators after this implementation runs.
     * Return null if any can be ran after this.
     */
    Class<? extends Item>[] runBeforeTypes();

}

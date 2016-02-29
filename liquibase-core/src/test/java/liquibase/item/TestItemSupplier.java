package liquibase.item;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.plugin.Plugin;

import java.util.List;

/**
 * Creates names and {@link ItemReference}s for tests
 */
public interface TestItemSupplier<T extends Item> extends Plugin, ExtensibleObject{

    int getPriority(Class<T> type, Scope scope);

    /**
     * Returns names for the given {@link Item} type.
     */
    List<String> getNames(Class<T> type, NameStrategy nameStrategy, Scope scope);

    /**
     * Returns {@link ItemReference}s for the given {@link Item} type.
     *
     * @param containers Create references using the given container(s). If null or empty, created references will have no container.
     */
    List<ItemReference> getReferences(Class<T> type, List<? extends ItemReference> containers, NameStrategy nameStrategy, Scope scope);

    /**
     * Determines the types of names returned by {@link TestDatabaseObjectSupplier#getNames(Class, NameStrategy, Scope)}
     */
    enum NameStrategy {
        COMPLEX_NAMES,
        SIMPLE_NAMES
    }
}

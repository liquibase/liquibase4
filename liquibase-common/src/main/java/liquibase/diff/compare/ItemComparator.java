package liquibase.diff.compare;

import liquibase.Scope;
import liquibase.diff.ObjectDifferences;
import liquibase.item.Item;
import liquibase.plugin.AbstractPlugin;
import liquibase.plugin.Plugin;

public interface ItemComparator extends Plugin {


    int getPriority(Class<? extends Item> type, Scope scope);

    <T extends Item> boolean isSameObject(T item1, T item2, Scope scope);

    <T extends Item> ObjectDifferences findDifferences(T item1, T item2, Scope scope);
}

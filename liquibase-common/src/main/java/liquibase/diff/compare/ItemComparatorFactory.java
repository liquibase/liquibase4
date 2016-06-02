package liquibase.diff.compare;

import liquibase.Scope;
import liquibase.item.Item;
import liquibase.plugin.AbstractPluginFactory;

public class ItemComparatorFactory extends AbstractPluginFactory<ItemComparator> {

    protected ItemComparatorFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<ItemComparator> getPluginClass() {
        return ItemComparator.class;
    }

    @Override
    protected int getPriority(ItemComparator obj, Scope scope, Object... args) {
        return obj.getPriority((Class<? extends Item>) args[0], scope);
    }

    public ItemComparator getComparator(Class<? extends Item> type, Scope scope) {
        return getPlugin(scope, type);
    }
}

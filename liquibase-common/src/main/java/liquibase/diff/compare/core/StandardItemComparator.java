package liquibase.diff.compare.core;

import liquibase.Scope;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.AbstractItemComparator;
import liquibase.item.Item;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StandardItemComparator extends AbstractItemComparator {

    @Override
    public int getPriority(Class<? extends Item> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }

    @Override
    public <T extends Item> boolean isSameObject(T item1, T item2, Scope scope) {
        return item1.toReference().equals(item2.toReference());
    }

    @Override
    public <T extends Item> ObjectDifferences findDifferences(T item1, T item2, Scope scope) {
        ObjectDifferences differences = null;
        Set<String> attributes = new HashSet<>();
        attributes.addAll(item1.getAttributes());
        attributes.addAll(item2.getAttributes());

        for (String attr : attributes) {
            Object value1 = item1.get(attr, Object.class);
            Object value2 = item2.get(attr, Object.class);

            if (!Objects.equals(value1, value2)) {
                if (differences == null) {
                    differences = new ObjectDifferences();
                }
                differences.addDifference(attr, value1, value2);
            }
        }
        return differences;
    }
}

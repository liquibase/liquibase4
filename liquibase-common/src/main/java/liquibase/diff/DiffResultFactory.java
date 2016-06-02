package liquibase.diff;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.diff.compare.ItemComparator;
import liquibase.diff.compare.ItemComparatorFactory;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.Item;
import liquibase.snapshot.Snapshot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DiffResultFactory implements SingletonObject{

    private final Scope factoryScope;

    protected DiffResultFactory(Scope factoryScope) {
        this.factoryScope = factoryScope;
    }

    public DiffResult diff(Snapshot referenceSnapshot, Snapshot currentSnapshot, Scope scope) {
        DiffResult result = new DiffResult();

        result.comparedTypes.addAll(referenceSnapshot.getTypes());
        result.comparedTypes.addAll(currentSnapshot.getTypes());

        for (Class<? extends Item> type : result.comparedTypes) {
            Collection<? extends Item> referenceItems = referenceSnapshot.get(type);
            Collection<? extends Item> currentItems = currentSnapshot.get(type);

            compare((Collection<Item>) referenceItems, (Collection<Item>) currentItems, result, (Class<Item>) type, scope);
        }

        Set<String> snapshotAttributes = new HashSet<>();
        snapshotAttributes.addAll(referenceSnapshot.getAttributes());
        snapshotAttributes.addAll(currentSnapshot.getAttributes());
        for (String attr : snapshotAttributes) {
            StringDiff diff = new StringDiff(referenceSnapshot.get(attr, String.class), currentSnapshot.get(attr, String.class));
            result.addSnapshotDifference(attr, diff);
        }

        return result;
    }

    protected <T extends Item> void compare(Collection<T> referenceItems, Collection<T> currentItems, DiffResult result, Class<T> type, Scope scope) {
        ItemComparator comparator = scope.getSingleton(ItemComparatorFactory.class).getComparator(type, scope);

        if (comparator == null) {
            throw new UnexpectedLiquibaseException("Could not find a comparator for "+type.getName());
        }

        for (T referenceItem : referenceItems) {
            T foundCurrentItem = null;
            for (T currentItem : currentItems) {
                if (comparator.isSameObject(referenceItem, currentItem, scope)) {
                    foundCurrentItem = currentItem;
                    break;
                }
            }

            if (foundCurrentItem == null) {
                result.addMissing(referenceItem);
            } else {
                ObjectDifferences differences = comparator.findDifferences(referenceItem, foundCurrentItem, scope);
                if (differences != null && differences.hasDifferences()) {
                    result.addChanged(referenceItem, differences);
                }
            }
        }

        for (T currentItem : currentItems) {
            boolean foundReferenceItem = false;

            for (T referenceItem : referenceItems) {
                if (comparator.isSameObject(referenceItem, currentItem, scope)) {
                    foundReferenceItem = true;
                    break;
                }
            }
            if (!foundReferenceItem) {
                result.addUnexpected(currentItem);
            }
        }
    }
}

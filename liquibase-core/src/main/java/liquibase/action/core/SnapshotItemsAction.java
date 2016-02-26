package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.action.QueryAction;
import liquibase.item.Item;
import liquibase.item.ItemReference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Snapshot objects.
 * The {@link SnapshotItemsAction#typeToSnapshot} parameter contains the type of object to snapshot.
 * The {@link SnapshotItemsAction#relatedTo} parameter contains the object for which all the given typeToSnapshot objects should be related to.
 * For example, if relatedTo is the table public.test_table and relatedTo is Column.class, the ActionResult will be a list of columns in the table public.test_table.
 * If relatedTo is the table public.test_table and relatedTo is Table.class, the ActionResult will be the table public_test_table.
 * If relatedTo is the schema "public" and relatedTo is Table.class, the ActionResult will be all tables in the public schema.
 * <br><br>
 * The Logic implementation should not fill in "nested" database objects. For example, Tables returned should not have any Column objects attached to them as a result of this Action.
 */
public class SnapshotItemsAction extends AbstractAction implements QueryAction {

    public Class<? extends Item> typeToSnapshot;
    public Set<ItemReference> relatedTo = new HashSet<>();

    public SnapshotItemsAction(ItemReference itemToSnapshot) {
        this.typeToSnapshot = itemToSnapshot.type;
        relatedTo.add(itemToSnapshot);
    }

    public SnapshotItemsAction(Class<? extends Item> typeToLookup, ItemReference... relatedTo) {
        this.typeToSnapshot = typeToLookup;
        if (relatedTo != null) {
            this.relatedTo.addAll(Arrays.asList(relatedTo));
        }
    }
}

package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.action.QueryAction;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;

/**
 * Action describing objects to snapshot.
 * The {@link SnapshotObjectsAction#typeToSnapshot} parameter contains the type of object to snapshot.
 * The {@link SnapshotObjectsAction#relatedTo} parameter contains the object for which all the given typeToSnapshot objects should be related to.
 * For example, if relatedTo is the table public.test_table and relatedTo is Column.class, the ActionResult will be a list of columns in the table public.test_table.
 * If relatedTo is the table public.test_table and relatedTo is Table.class, the ActionResult will be the table public_test_table.
 * If relatedTo is the schema "public" and relatedTo is Table.class, the ActionResult will be all tables in the public schema.
 * <br><br>
 * The Logic implementation should not fill in "nested" database objects. For example, Tables returned should not have any Column objects attached to them as a result of this Action.
 */
public class SnapshotObjectsAction extends AbstractAction implements QueryAction {

    public Class<? extends LiquibaseObject> typeToSnapshot;
    public ObjectReference relatedTo;

    public SnapshotObjectsAction(ObjectReference objectToSnapshot) {
        this.typeToSnapshot = objectToSnapshot.type;
        relatedTo = objectToSnapshot;
    }

    public SnapshotObjectsAction(Class<? extends LiquibaseObject> typeToLookup, ObjectReference relatedTo) {
        this.typeToSnapshot = typeToLookup;
        this.relatedTo = relatedTo;
    }
}

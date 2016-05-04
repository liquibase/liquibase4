package liquibase.command.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.command.AbstractLiquibaseCommand;
import liquibase.command.CommandResult;
import liquibase.item.core.*;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;
import liquibase.item.Item;
import liquibase.item.ItemReference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Snapshots all objects related to {@link #relatedObjects}.
 */
public class SnapshotCommand extends AbstractLiquibaseCommand<SnapshotCommand.SnapshotCommandResult> {

    public Set<ItemReference> relatedObjects = new HashSet<>();

    public SnapshotCommand() {
    }

    public SnapshotCommand(ItemReference... relatedObjects) {
        if (relatedObjects != null) {
            this.relatedObjects.addAll(Arrays.asList(relatedObjects));
        }
    }

    @Override
    public String getName() {
        return "snapshot";
    }

    @Override
    protected SnapshotCommandResult run(Scope scope) throws Exception {

        Set<Class<? extends Item>> types = new HashSet((List) Arrays.asList(Table.class, View.class, ForeignKey.class, StoredProcedure.class)); //TODO: scope.getSingleton(DatabaseObjectFactory.class).getStandardTypes();

        if (scope.getDatabase().supports(Sequence.class, scope)) {
            types.add(Sequence.class);
        }

        Snapshot snapshot = new Snapshot(scope);

        for (ItemReference related : relatedObjects) {
            for (Class type : types) {
                snapshot.addAll(scope.getSingleton(SnapshotFactory.class).snapshotAll(type, related, scope));
            }
        }

        return new SnapshotCommandResult(snapshot);
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        return new ValidationErrors(this);
    }

    public static class SnapshotCommandResult extends CommandResult {

        public Snapshot snapshot;

        public SnapshotCommandResult() {
        }

        public SnapshotCommandResult(Snapshot snapshot) {
            this.snapshot = snapshot;
        }
    }
}

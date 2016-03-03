package liquibase.command;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.DropForeignKeysAction;
import liquibase.action.core.DropSequencesAction;
import liquibase.action.core.DropTablesAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.database.Database;
import liquibase.item.ItemReference;
import liquibase.item.core.ForeignKey;
import liquibase.item.core.ForeignKeyReference;
import liquibase.item.core.Sequence;
import liquibase.item.core.Table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Drops all objects in the given {@link #containers}.
 */
public class DropAllCommand extends AbstractCommand {

    public Set<ItemReference> containers = new HashSet<>();

    public DropAllCommand() {
    }

    public DropAllCommand(ItemReference... containers) {
        if (containers != null) {
            this.containers.addAll(Arrays.asList(containers));
        }
    }

    @Override
    public String getName() {
        return "dropAll";
    }


    @Override
    protected CommandResult run(Scope scope) throws Exception {
        SnapshotCommand snapshotCommand = new SnapshotCommand();
        snapshotCommand.relatedObjects.addAll(containers);
        SnapshotCommand.SnapshotCommandResult snapshotResult = snapshotCommand.execute(scope);

        for (ForeignKey foreignKey : snapshotResult.snapshot.get(ForeignKey.class)) {
            scope.getSingleton(ActionExecutor.class).execute(new DropForeignKeysAction((ForeignKeyReference) foreignKey.toReference()), scope);
        }

        for (Table table : snapshotResult.snapshot.get(Table.class)) {
            scope.getSingleton(ActionExecutor.class).execute(new DropTablesAction(table.toReference()), scope);
        }

        if (scope.getDatabase().supports(Database.Feature.SEQUENCES, scope)) {
            for (Sequence seq : snapshotResult.snapshot.get(Sequence.class)) {
                scope.getSingleton(ActionExecutor.class).execute(new DropSequencesAction(seq.toReference()), scope);
            }
        }

        return new CommandResult();
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        return new ValidationErrors(this);
    }

}

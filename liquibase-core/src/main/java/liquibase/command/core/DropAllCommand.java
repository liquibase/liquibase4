package liquibase.command.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.*;
import liquibase.actionlogic.ActionExecutor;
import liquibase.command.AbstractLiquibaseCommand;
import liquibase.command.CommandResult;
import liquibase.item.ItemReference;
import liquibase.item.core.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Drops all objects in the given {@link #containers}.
 */
public class DropAllCommand extends AbstractLiquibaseCommand {

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

        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);
        for (ForeignKey foreignKey : snapshotResult.snapshot.get(ForeignKey.class)) {
            executor.execute(new DropForeignKeysAction((ForeignKeyReference) foreignKey.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        for (View view : snapshotResult.snapshot.get(View.class)) {
            executor.execute(new DropViewsAction(view.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        for (Table table : snapshotResult.snapshot.get(Table.class)) {
            executor.execute(new DropTablesAction(table.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        if (scope.getDatabase().supports(Sequence.class, scope)) {
            for (Sequence seq : snapshotResult.snapshot.get(Sequence.class)) {
                executor.execute(new DropSequencesAction(seq.toReference()), scope);
            }
        }

        if (scope.getDatabase().supports(StoredProcedure.class, scope)) {
            for (StoredProcedure proc : snapshotResult.snapshot.get(StoredProcedure.class)) {
                executor.execute(new DropStoredProceduresAction(proc.toReference()), scope);
            }
        }

        executor.execute(new CommitAction(), scope);

        return new CommandResult();
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        return new ValidationErrors(this);
    }

}

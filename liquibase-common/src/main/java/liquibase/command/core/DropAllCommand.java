package liquibase.command.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.*;
import liquibase.actionlogic.ActionExecutor;
import liquibase.command.AbstractLiquibaseCommand;
import liquibase.command.AbstractSnapshotCommand;
import liquibase.command.CommandResult;
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.snapshot.Snapshot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Drops all objects in the given {@link #relatedObjects}.
 */
public class DropAllCommand extends AbstractSnapshotCommand {

    public DropAllCommand() {
    }

    public DropAllCommand(ItemReference[] relatedObjects) {
        super(relatedObjects);
    }

    @Override
    public String getName() {
        return "dropAll";
    }


    @Override
    protected CommandResult run(Scope scope) throws Exception {
        scope = setupDatabase(scope);
        Snapshot snapshot = snapshot(scope);

        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);
        for (ForeignKey foreignKey : snapshot.get(ForeignKey.class)) {
            executor.execute(new DropForeignKeysAction((ForeignKeyReference) foreignKey.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        for (View view : snapshot.get(View.class)) {
            executor.execute(new DropViewsAction(view.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        for (Table table : snapshot.get(Table.class)) {
            executor.execute(new DropTablesAction(table.toReference()), scope);
        }
        executor.execute(new CommitAction(), scope);

        if (scope.getDatabase().supports(Sequence.class, scope)) {
            for (Sequence seq : snapshot.get(Sequence.class)) {
                executor.execute(new DropSequencesAction(seq.toReference()), scope);
            }
        }

        if (scope.getDatabase().supports(StoredProcedure.class, scope)) {
            for (StoredProcedure proc : snapshot.get(StoredProcedure.class)) {
                executor.execute(new DropStoredProceduresAction(proc.toReference()), scope);
            }
        }

        executor.execute(new CommitAction(), scope);

        return new CommandResult();
    }
}

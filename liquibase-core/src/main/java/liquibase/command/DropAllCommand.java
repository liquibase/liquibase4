package liquibase.command;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.DropForeignKeyAction;
import liquibase.action.core.DropTableAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Drops all objects in the given {@link #containers}.
 */
public class DropAllCommand extends AbstractCommand {

    public Set<ObjectReference> containers = new HashSet<>();

    public DropAllCommand() {
    }

    public DropAllCommand(ObjectReference... containers) {
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
            scope.getSingleton(ActionExecutor.class).execute(new DropForeignKeyAction((ForeignKey.ForeignKeyReference) foreignKey.toReference()), scope);
        }

        for (Table table : snapshotResult.snapshot.get(Table.class)) {
            scope.getSingleton(ActionExecutor.class).execute(new DropTableAction(table.toReference()), scope);
        }

        return new CommandResult();
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        return new ValidationErrors(this);
    }

}

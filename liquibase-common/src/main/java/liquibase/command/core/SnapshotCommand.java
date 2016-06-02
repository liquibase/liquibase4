package liquibase.command.core;

import liquibase.ExtensibleObjectAttribute;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.command.AbstractDatabaseCommand;
import liquibase.command.AbstractSnapshotCommand;
import liquibase.command.CommandResult;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.parser.UnparserFactory;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Snapshots all objects related to {@link #relatedObjects}.
 */
public class SnapshotCommand extends AbstractSnapshotCommand<SnapshotCommand.SnapshotCommandResult> {

    @ExtensibleObjectAttribute(description = "Format to output snapshot as. Defaults to 'xml'", required = true)
    public String snapshotFormat = "xml";


    public SnapshotCommand() {
    }

    public SnapshotCommand(ItemReference... relatedObjects) {
        super(relatedObjects);

    }

    @Override
    public String getName() {
        return "snapshot";
    }

    @Override
    protected SnapshotCommandResult run(Scope scope) throws Exception {

        scope = setupDatabase(scope);

        Snapshot snapshot = snapshot(scope);

        return new SnapshotCommandResult(snapshot);
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        return new ValidationErrors(this);
    }

    public class SnapshotCommandResult extends CommandResult {

        public Snapshot snapshot;

        public SnapshotCommandResult() {
        }

        public SnapshotCommandResult(Snapshot snapshot) {
            this.snapshot = snapshot;
        }

        @Override
        public String print(Scope scope) throws LiquibaseException {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            scope.getSingleton(UnparserFactory.class).unparse(this.snapshot, outputStream, "stdout."+snapshotFormat, scope);

            return new String(outputStream.toByteArray());
        }
    }
}

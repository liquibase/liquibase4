package liquibase.command.core;

import liquibase.ExtensibleObjectAttribute;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.command.AbstractDatabaseCommand;
import liquibase.command.CommandResult;
import liquibase.exception.LiquibaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.parser.UnparserFactory;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Snapshots all objects related to {@link #relatedObjects}.
 */
public class SnapshotCommand extends AbstractDatabaseCommand<SnapshotCommand.SnapshotCommandResult> {

    public Set<ItemReference> relatedObjects = new HashSet<>();

    @ExtensibleObjectAttribute(description = "Format to output snapshot as. Defaults to 'xml'", required = true)
    public String snapshotFormat = "xml";


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

        scope = setupDatabase(scope);

        Set<Class<? extends Item>> types = new HashSet((List) Arrays.asList(Table.class, View.class, ForeignKey.class, StoredProcedure.class)); //TODO: scope.getSingleton(DatabaseObjectFactory.class).getStandardTypes();

        if (scope.getDatabase().supports(Sequence.class, scope)) {
            types.add(Sequence.class);
        }

        Snapshot snapshot = new Snapshot(scope);

        Set<ItemReference> relatedObjects = this.relatedObjects;
        if (relatedObjects.size() == 0) {
            SchemaReference schema = new SchemaReference(scope.getDatabase().getConnection().getSchema());
            relatedObjects.add(schema);
        }


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

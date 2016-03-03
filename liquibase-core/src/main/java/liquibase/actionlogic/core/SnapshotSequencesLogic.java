package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.util.Validate;

import java.math.BigInteger;
import java.util.List;

public class SnapshotSequencesLogic extends AbstractSnapshotDatabaseObjectsLogic<Sequence> {

    @Override
    protected Class<Sequence> getTypeToSnapshot() {
        return Sequence.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supports(Database.Feature.SEQUENCES, scope);
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Sequence.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        SequenceReference sequenceRef;
        if (relatedTo instanceof CatalogReference) {
            if (!scope.getDatabase().supports(Catalog.class, scope)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            sequenceRef = new SequenceReference(null, new SchemaReference(null, (CatalogReference) relatedTo));
        } else if (relatedTo instanceof SchemaReference) {
            sequenceRef = new SequenceReference(null, (SchemaReference) relatedTo);
        } else if (relatedTo instanceof SequenceReference) {
            sequenceRef = (SequenceReference) relatedTo;
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = sequenceRef.asList(3);

        if (scope.getDatabase().supports(Catalog.class, scope)) {
            return createSnapshotAction(nameParts.get(0), nameParts.get(1), nameParts.get(2), scope);
        } else {
            return createSnapshotAction(null, nameParts.get(1), nameParts.get(2), scope);
        }
    }

    protected Action createSnapshotAction(String catalogName, String schemaName, String sequenceName, Scope scope) {
        SelectDataAction action = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "SEQUENCES"),
                new SelectDataAction.SelectedColumn("SEQUENCE_NAME"),
                new SelectDataAction.SelectedColumn("START_VALUE"),
                new SelectDataAction.SelectedColumn("MIN_VALUE"),
                new SelectDataAction.SelectedColumn("MAX_VALUE"),
                new SelectDataAction.SelectedColumn("INCREMENT"),
                new SelectDataAction.SelectedColumn("CACHE_SIZE"),
                new SelectDataAction.SelectedColumn("IS_CYCLE")
        );


        if (sequenceName != null) {
            action.addWhere("SEQUENCE_NAME=" + scope.getDatabase().quoteString(sequenceName, scope));
        }
        if (schemaName != null) {
            action.addColumn(new SelectDataAction.SelectedColumn("SEQUENCE_SCHEMA"));
            action.addWhere("SEQUENCE_SCHEMA=" + scope.getDatabase().quoteString(schemaName, scope));
        }
        if (catalogName != null) {
            action.addColumn(new SelectDataAction.SelectedColumn("SEQUENCE_CATALOG"));
            action.addWhere("SEQUENCE_CATALOG=" + scope.getDatabase().quoteString(catalogName, scope));
        }

        return action;
    }

    @Override
    protected Sequence convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        Sequence seq = new Sequence();
        seq.name = row.get("SEQUENCE_NAME", String.class);

        String catalogName = row.get("SEQUENCE_CATALOG", String.class);
        String schemaName = row.get("SEQUENCE_SCHEMA", String.class);

        if (schemaName != null || catalogName != null) {
            seq.schema = new SchemaReference(catalogName, schemaName);
        }
        seq.startValue = row.get("START_VALUE", BigInteger.class);
        seq.minValue = row.get("MIN_VALUE", BigInteger.class);
        seq.maxValue = row.get("MAX_VALUE", BigInteger.class);
        seq.cacheSize = row.get("CACHE_SIZE", BigInteger.class);
        seq.incrementBy = row.get("INCREMENT", BigInteger.class);
        seq.cycle = row.get("IS_CYCLE", Boolean.class);
        seq.ordered = row.get("IS_ORDERED", Boolean.class);

        return seq;
    }


}

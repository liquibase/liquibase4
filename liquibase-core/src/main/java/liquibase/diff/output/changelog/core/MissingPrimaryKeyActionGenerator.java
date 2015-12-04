package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AddPrimaryKeysAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;

import java.util.ArrayList;
import java.util.List;

public class MissingPrimaryKeyActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (PrimaryKey.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends LiquibaseObject>[] runAfterTypes() {
        return new Class[] {
                Table.class,
                Column.class
        };

    }

    @Override
    public Class<? extends LiquibaseObject>[] runBeforeTypes() {
        return new Class[] {
                Index.class
        };
    }

    @Override
    public List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        PrimaryKey pk = (PrimaryKey) missingObject;

        ArrayList<AddPrimaryKeysAction> actions = new ArrayList<>();
        actions.add(new AddPrimaryKeysAction(pk));

        return actions;
//        List<Change> returnList = new ArrayList<Change>();

//        PrimaryKey pk = (PrimaryKey) missingObject;
//
//        AddPrimaryKeyChange change = new AddPrimaryKeyChange();
//        change.setTableName(pk.getTable().getSimpleName());
//        if (control.getIncludeCatalog()) {
//            change.setCatalogName(pk.getTable().getSchema().getCatalogName());
//        }
//        if (control.getIncludeSchema()) {
//            change.setSchemaName(pk.getTable().getSchema().getSimpleName());
//        }
//        change.setConstraintName(pk.getSimpleName());
////        change.setColumnNames(pk.getColumnNames());
//        if (control.getIncludeTablespace()) {
//            change.setTablespace(pk.getTablespace());
//        }

//todo: action refactoring        if (referenceDatabase instanceof MSSQLDatabase && pk.getBackingIndex() != null && pk.getBackingIndex().getClustered() != null && !pk.getBackingIndex().getClustered()) {
//            change.setClustered(false);
//        }

//        if (comparisonDatabase instanceof OracleDatabase) {
//            Index backingIndex = pk.getBackingIndex();
//            if (backingIndex != null && backingIndex.getName() != null) {
//                returnList.addAll(Arrays.asList(ChangeGeneratorFactory.getInstance().fixMissing(backingIndex, control, referenceDatabase, comparisonDatabase)));
//
//                change.setForIndexName(backingIndex.getName());
//                Schema schema = backingIndex.getSchema();
//                if (schema != null) {
//                    if (control.getIncludeCatalog()) {
//                        change.setForIndexCatalogName(schema.getCatalogName());
//                    }
//                    if (control.getIncludeSchema()) {
//                        change.setForIndexSchemaName(schema.getName());
//                    }
//                }
//            }
//        }

//        control.setAlreadyHandledMissing(pk.getBackingIndex());
//        returnList.add(change);
//
//        return returnList.toArray(new Change[returnList.size()]);

    }
}

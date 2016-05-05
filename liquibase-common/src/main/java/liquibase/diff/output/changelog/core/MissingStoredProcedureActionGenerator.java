package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateStoredProceduresAction;
import liquibase.database.DatabaseFactory;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.Item;
import liquibase.item.core.Column;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.StoredProcedure;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;

import java.util.Collections;
import java.util.List;

public class MissingStoredProcedureActionGenerator implements MissingObjectActionGenerator<StoredProcedure> {

    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (StoredProcedure.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends Item>[] runAfterTypes() {
        return null;
    }

    @Override
    public Class<? extends Item>[] runBeforeTypes() {
        return null;
    }

    @Override
    public List<? extends Action> fixMissing(StoredProcedure missingStoredProcedure, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (targetSnapshot.getScopeCreatedUnder().getDatabase().isLiquibaseObject(missingStoredProcedure.toReference(), scope)) {
            return null;
        }

        CreateStoredProceduresAction action = createCreateStoredProceduresAction();
        action.procedures.add((StoredProcedure) missingStoredProcedure.clone());

        return Collections.singletonList((Action) action);
    }

    protected CreateStoredProceduresAction createCreateStoredProceduresAction() {
        return new CreateStoredProceduresAction();
    }
}

package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateIndexesAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;

import java.util.ArrayList;
import java.util.List;

public class MissingIndexActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (Index.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends LiquibaseObject>[] runAfterTypes() {
        return new Class[] {
                Table.class,
                Column.class,
        };
    }

    @Override
    public Class<? extends LiquibaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        Index index = (Index) missingObject;

        ArrayList<CreateIndexesAction> actions = new ArrayList<>();
        actions.add(new CreateIndexesAction(index));

        return actions;
    }
}

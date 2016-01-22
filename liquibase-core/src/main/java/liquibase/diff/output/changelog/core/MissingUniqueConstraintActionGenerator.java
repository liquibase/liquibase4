package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AddUniqueConstraintsAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.snapshot.Snapshot;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.UniqueConstraint;
import liquibase.structure.core.Table;

import java.util.ArrayList;
import java.util.List;

public class MissingUniqueConstraintActionGenerator implements MissingObjectActionGenerator {


    @Override
    public int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (UniqueConstraint.class.isAssignableFrom(objectType)) {
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
        UniqueConstraint uq = (UniqueConstraint) missingObject;

        ArrayList<AddUniqueConstraintsAction> actions = new ArrayList<>();
        actions.add(new AddUniqueConstraintsAction(uq));

        return actions;
    }
}

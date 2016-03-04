package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateViewsAction;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.item.Item;
import liquibase.item.core.View;
import liquibase.snapshot.Snapshot;

import java.util.Collections;
import java.util.List;

public class MissingViewActionGenerator implements MissingObjectActionGenerator<View> {

    @Override
    public int getPriority(Class<? extends Item> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (View.class.isAssignableFrom(objectType)) {
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
    public List<? extends Action> fixMissing(View missingView, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        return Collections.singletonList(new CreateViewsAction((View) missingView.clone()));
    }
}

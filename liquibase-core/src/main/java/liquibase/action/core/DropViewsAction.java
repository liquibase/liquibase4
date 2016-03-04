package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drops existing views.
 */
public class DropViewsAction extends AbstractAction {

    public List<RelationReference> views = new ArrayList<>();

    public DropViewsAction() {
    }

    public DropViewsAction(RelationReference... views) {
        if (views != null) {
            this.views.addAll(Arrays.asList(views));
        }
    }
}

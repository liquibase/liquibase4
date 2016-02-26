package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.IndexReference;

/**
 * Drops an existing index.
 */
public class DropIndexAction extends AbstractAction {

    public IndexReference index;

    public DropIndexAction() {
    }

    public DropIndexAction(IndexReference index) {
        this.index = index;
    }
}

package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Index;

/**
 * Drops an existing index.
 */
public class DropIndexAction extends AbstractAction {

    public Index.IndexReference index;
    public ObjectReference associatedWith;

    public DropIndexAction() {
    }

    public DropIndexAction(Index.IndexReference index) {
        this.index = index;
    }
}

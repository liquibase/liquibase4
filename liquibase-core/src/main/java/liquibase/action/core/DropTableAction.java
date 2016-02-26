package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;

/**
 * Drop an existing table
 */
public class DropTableAction extends AbstractAction {
    public RelationReference table;
    public Boolean cascadeConstraints;

    public DropTableAction() {
    }

    public DropTableAction(RelationReference table) {
        this.table = table;
    }


}
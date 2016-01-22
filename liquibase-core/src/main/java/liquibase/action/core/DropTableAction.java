package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;

public class DropTableAction extends AbstractAction {
    public ObjectReference table;
    public Boolean cascadeConstraints;

    public DropTableAction() {
    }

    public DropTableAction(ObjectReference table) {
        this.table = table;
    }


}
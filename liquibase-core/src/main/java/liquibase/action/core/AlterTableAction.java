package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.util.StringClauses;

public class AlterTableAction extends AbstractAction {

    public ObjectReference table;
    public StringClauses newDefinition;

    public AlterTableAction() {
    }

    public AlterTableAction(ObjectReference table, StringClauses newDefinition) {
        this.table = table;
        this.newDefinition = newDefinition;
    }

}

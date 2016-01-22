package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.util.StringClauses;

public class AlterColumnAction extends AbstractAction {

    public ObjectReference column;
    public StringClauses newDefinition;

    public AlterColumnAction() {
    }

    public AlterColumnAction(ObjectReference column, StringClauses newDefinition) {
        this.column = column;
        this.newDefinition = newDefinition;
    }

}

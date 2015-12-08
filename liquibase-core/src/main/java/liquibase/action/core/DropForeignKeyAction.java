package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;

public class DropForeignKeyAction extends AbstractAction {

    public ObjectReference baseTableName;
    public String constraintName;

    public DropForeignKeyAction() {
    }

    public DropForeignKeyAction(String constraintName, ObjectReference baseTableName) {
        this.constraintName = constraintName;
        this.baseTableName = baseTableName;
    }
}

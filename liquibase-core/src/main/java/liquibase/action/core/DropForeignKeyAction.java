package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.ForeignKey;

/**
 * Drops an existing foreign key.
 */
public class DropForeignKeyAction extends AbstractAction {

    public ForeignKey.ForeignKeyReference foreignKey;

    public DropForeignKeyAction() {
    }

    public DropForeignKeyAction(ForeignKey.ForeignKeyReference foreignKey) {
        this.foreignKey = foreignKey;
    }
}

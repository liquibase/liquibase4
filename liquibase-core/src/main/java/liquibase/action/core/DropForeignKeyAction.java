package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ForeignKeyReference;

/**
 * Drops an existing foreign key.
 */
public class DropForeignKeyAction extends AbstractAction {

    public ForeignKeyReference foreignKey;

    public DropForeignKeyAction() {
    }

    public DropForeignKeyAction(ForeignKeyReference foreignKey) {
        this.foreignKey = foreignKey;
    }
}

package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;

/**
 * Drops all foreign key constraints on a table.
 */
public class DropAllForeignKeysAction extends AbstractAction {

    public RelationReference table;

}

package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.ItemReference;
import liquibase.item.core.RelationReference;

/**
 * Renames a table. {@link liquibase.actionlogic.ActionLogic} implementations should handle the case where the schema changes as part of the rename.
 */
public class RenameTableAction extends AbstractAction {
    public RelationReference oldName;
    public RelationReference newName;
}

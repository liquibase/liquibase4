package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.SequenceReference;

/**
 * Renames a sequence. {@link liquibase.actionlogic.ActionLogic} implementations should handle the case where the schema changes as part of the rename.
 */
public class RenameSequenceAction extends AbstractAction {
    public SequenceReference oldName;
    public SequenceReference newName;
}

package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;

/**
 * Renames a view. {@link liquibase.actionlogic.ActionLogic} implementations should handle the case where the schema changes as part of the rename.
 */
public class RenameViewAction extends AbstractAction {
    public ObjectReference oldViewName;
    public ObjectReference newViewName;
}

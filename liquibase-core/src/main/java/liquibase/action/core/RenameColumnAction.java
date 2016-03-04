package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.core.RelationReference;
import liquibase.util.StringClauses;

/**
 * Renames a column in a table or view.
 */
public class RenameColumnAction extends AbstractAction {
    public RelationReference relation;

    public String oldName;
    public String newName;

    public StringClauses columnDefinition;
}

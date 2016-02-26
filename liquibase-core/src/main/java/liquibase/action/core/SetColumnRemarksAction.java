package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;

/**
 * Sets remarks/comments on a column.
 */
public class SetColumnRemarksAction extends AbstractAction {
    public ColumnReference column;
    public String remarks;
}

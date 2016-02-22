package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.Column;

/**
 * Sets remarks/comments on a column.
 */
public class SetColumnRemarksAction extends AbstractAction {
    public Column.ColumnReference column;
    public String remarks;
}

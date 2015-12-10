package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Column;

public class SetColumnRemarksAction extends AbstractAction {
    public Column.ColumnReference columnName;
    public String remarks;
}

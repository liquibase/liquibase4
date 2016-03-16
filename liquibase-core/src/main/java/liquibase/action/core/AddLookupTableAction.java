package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

public class AddLookupTableAction extends AbstractAction {
    public ColumnReference existingColumn;
    public ColumnReference newColumn;
    public DataType newColumnDataType;
    public String foreignKeyName;
    public String primaryKeyName;

}

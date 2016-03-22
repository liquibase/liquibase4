package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

public class ModifyDataTypeAction extends AbstractAction {

    public ColumnReference column;
    public DataType newDataType;

}

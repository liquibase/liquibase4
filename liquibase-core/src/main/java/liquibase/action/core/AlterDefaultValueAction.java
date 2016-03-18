package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

/**
 * Sets the default value on an existing column. To drop the default value, set the value to null;
 */
public class AlterDefaultValueAction extends AbstractAction {
    public ColumnReference column;
    public DataType columnDataType;
    public Object defaultValue;

    public AlterDefaultValueAction() {
    }

    public AlterDefaultValueAction(ColumnReference column, Object defaultValue, DataType columnDataType) {
        this.column = column;
        this.defaultValue = defaultValue;
        this.columnDataType = columnDataType;
    }
}

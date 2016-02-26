package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

/**
 * Drops the default value on a column.
 */
public class DropDefaultValueAction extends AbstractAction {
    public ColumnReference column;
    public DataType columnDataType;

    public DropDefaultValueAction() {
    }

    public DropDefaultValueAction(ColumnReference column, DataType columnDataType) {
        this.column = column;
        this.columnDataType = columnDataType;
    }
}

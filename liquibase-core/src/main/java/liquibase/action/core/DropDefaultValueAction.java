package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Column;
import liquibase.structure.datatype.DataType;

/**
 * Drops the default value on a column.
 */
public class DropDefaultValueAction extends AbstractAction {
    public Column.ColumnReference column;
    public DataType columnDataType;

    public DropDefaultValueAction() {
    }

    public DropDefaultValueAction(Column.ColumnReference column, DataType columnDataType) {
        this.column = column;
        this.columnDataType = columnDataType;
    }
}

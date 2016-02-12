package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.Column;
import liquibase.structure.datatype.DataType;

/**
 * Makes a column an auto-increment AKA identity column.
 */
public class AddAutoIncrementAction extends AbstractAction {

    public Column.ColumnReference column;
    public DataType dataType;
    public Column.AutoIncrementInformation autoIncrementInformation;

    public AddAutoIncrementAction() {
    }

    public AddAutoIncrementAction(Column.ColumnReference column, DataType dataType, Column.AutoIncrementInformation autoIncrementInformation) {
        this.column = column;
        this.dataType = dataType;
        this.autoIncrementInformation = autoIncrementInformation;
    }
}

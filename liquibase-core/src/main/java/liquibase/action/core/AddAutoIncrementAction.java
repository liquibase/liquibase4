package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

/**
 * Makes a column an auto-increment AKA identity column.
 */
public class AddAutoIncrementAction extends AbstractAction {

    public ColumnReference column;
    public DataType dataType;
    public Column.AutoIncrementInformation autoIncrementInformation;

    public AddAutoIncrementAction() {
    }

    public AddAutoIncrementAction(ColumnReference column, DataType dataType, Column.AutoIncrementInformation autoIncrementInformation) {
        this.column = column;
        this.dataType = dataType;
        this.autoIncrementInformation = autoIncrementInformation;
    }
}

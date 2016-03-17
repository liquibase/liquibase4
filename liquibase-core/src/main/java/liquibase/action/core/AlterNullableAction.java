package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;

/**
 * Adds or removes a not null constraint to an existing column based on the {@link #nullable} value.
 * If {@link #valueForExistingNulls} is not null, all null values for the column are updated to that value before the constraint is applied.
 */
public class AlterNullableAction extends AbstractAction {

    public Boolean nullable;
    public ColumnReference column;
    public String constraintName;
    public DataType columnDataType;
    public Object valueForExistingNulls;

}

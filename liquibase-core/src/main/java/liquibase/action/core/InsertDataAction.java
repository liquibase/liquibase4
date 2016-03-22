package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RowData;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Insert data into a database.
 * Data can be conditionally inserted or updated using the {@link #columnsForUpdateCheck}
 */
public class InsertDataAction extends AbstractAction {


    public List<RowData> data = new ArrayList<>();

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-databse compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();

    public InsertDataAction() {
    }

    public InsertDataAction(List<RowData> data) {
        this.data = data;
    }

    public InsertDataAction(RowData... data) {
        this.data = Arrays.asList(CollectionUtil.createIfNull(data));
    }
}

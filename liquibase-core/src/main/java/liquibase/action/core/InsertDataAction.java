package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RowData;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Insert data into a database.
 */
public class InsertDataAction extends AbstractAction {


    public List<RowData> data = new ArrayList<>();

    public InsertDataAction() {
    }

    public InsertDataAction(List<RowData> data) {
        this.data = data;
    }

    public InsertDataAction(RowData... data) {
        this.data = Arrays.asList(CollectionUtil.createIfNull(data));
    }
}

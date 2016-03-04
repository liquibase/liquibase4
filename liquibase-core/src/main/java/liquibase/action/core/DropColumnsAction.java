package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ColumnReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropColumnsAction extends AbstractAction {

    public List<ColumnReference> columns = new ArrayList<>();

    public DropColumnsAction() {
    }

    public DropColumnsAction(ColumnReference... columns) {
        if (columns != null) {
            this.columns.addAll(Arrays.asList(columns));
        }
    }
}

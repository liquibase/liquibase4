package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.UniqueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddColumnsAction extends AbstractAction {

    public PrimaryKey primaryKey;
    public List<Column> columns = new ArrayList<>();
    public List<UniqueConstraint> uniqueConstraints = new ArrayList<>();
    public List<ForeignKey> foreignKeys = new ArrayList<>();

    public AddColumnsAction() {
    }

    public AddColumnsAction(Column... columns) {
        if (columns != null) {
            this.columns = new ArrayList<>(Arrays.asList(columns));
        }
    }
}

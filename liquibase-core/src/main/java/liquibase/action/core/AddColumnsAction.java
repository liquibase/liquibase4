package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.Column;
import liquibase.item.core.ForeignKey;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.UniqueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds columns to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are columns added to multiple tables.
 * For performance and/or necessity, primary keys, unique constraints and foreign keys can be added along with the columns.
 */
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

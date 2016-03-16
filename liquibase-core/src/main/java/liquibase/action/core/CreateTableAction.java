package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.*;
import liquibase.item.datatype.DataType;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a table along with the specified columns, primary key, foreignKeys, and uniqueConstraints.
 * ActionLogic implementations may create everything in a single statement or multiple statements as they see fit.
 */
public class CreateTableAction extends AbstractAction {

    public Table table;
    public List<Column> columns = new ArrayList<>();
    public PrimaryKey primaryKey;
    public List<ForeignKey> foreignKeys;
    public List<UniqueConstraint> uniqueConstraints;


    public CreateTableAction() {
    }


    public CreateTableAction(Table table) {
        this.table = table;
    }

    public CreateTableAction addColumn(ColumnReference column, DataType type) {
        return addColumn(new Column(column.name, column.getRelation(), type, true));
    }

    public CreateTableAction addColumn(String columnName, DataType type) {
        return addColumn(new Column(columnName, table.toReference(), type, true));
    }

    public CreateTableAction addColumn(Column column) {
        columns.add(column);

        return this;
    }

    public CreateTableAction setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }
}

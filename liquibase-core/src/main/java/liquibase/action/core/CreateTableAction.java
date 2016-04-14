package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.*;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.preprocessor.core.changelog.StandardActionPreprocessor;

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

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(CreateTableAction.class) {

            @Override
            public Class<? extends ParsedNodePreprocessor>[] mustBeAfter() {
                return new Class[] {
                        StandardActionPreprocessor.class
                };
            }

            @Override
            protected void processActionNode(ParsedNode actionNode) throws ParseException {
                super.processActionNode(actionNode);
                ParsedNode tableNode = actionNode.getChild("table", true);

                actionNode.moveChildren("relation", tableNode);
                actionNode.moveChildren("tablespace", tableNode);
                actionNode.moveChildren("remarks", tableNode);

                RelationReference relation = new RelationReference(Table.class, tableNode.getChildValue("name", String.class, false));

                ParsedNode columnsNode = actionNode.getChild("columns", true);
                actionNode.moveChildren("column", columnsNode);
                for (ParsedNode column : columnsNode.getChildren("column", false)) {
                    column.addChild("relation").setValue(relation);
                }
            }
        };
    }
}

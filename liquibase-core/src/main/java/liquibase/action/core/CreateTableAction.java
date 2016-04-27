package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.*;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

import java.util.*;

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
    public List<CheckConstraint> checkConstraints;


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
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(CreateTableAction.class) {

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode tableNode = actionNode.getChild("table", true);

                        actionNode.moveChildren("tableName", tableNode);
                        actionNode.moveChildren("schemaName", tableNode);
                        actionNode.moveChildren("catalogName", tableNode);
                        actionNode.moveChildren("tablespace", tableNode);
                        actionNode.moveChildren("remarks", tableNode);

                        tableNode.getChild("tableName", false).name = "name";
                        convertToSchemaReferenceNode("catalogName", "schemaName", tableNode);

                        ParsedNode columnsNode = actionNode.getChild("columns", true);
                        actionNode.moveChildren("column", columnsNode);

                        AddColumnsAction.AddColumnsPreprocessor addColumnsPreprocessor = new AddColumnsAction.AddColumnsPreprocessor();

                        ParsedNode tmpRelationRef = actionNode.addChild("tmpRelationRef");
                        tableNode.copyChildren("name", tmpRelationRef);
                        tableNode.copyChildren("schema", tmpRelationRef);
                        tmpRelationRef.renameChildren("schema", "container");

                        for (ParsedNode column : columnsNode.getChildren("column", false)) {
                            addColumnsPreprocessor.fixColumn(column, tmpRelationRef, actionNode);
                        }

                        tmpRelationRef.remove();

                        for (ParsedNode computed : actionNode.getChildren("computed", true)) {
                            computed.name = "virtual";
                        }
                    }


                }
        };
    }


}

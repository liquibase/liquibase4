package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ColumnReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

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

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropColumnsAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropColumn"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

                ParsedNode columnNameNode = actionNode.getChild("columnName", false);
                if (columnNameNode != null) {
                    ParsedNode columnNode = actionNode.addChild("column");
                    columnNameNode.moveTo(columnNode);
                }

                ParsedNode columns = actionNode.getChild("columns", true);
                actionNode.moveChildren("column", columns);

                for (ParsedNode column : columns.getChildren("column", false)) {
                    column.renameChildren("columnName", "name");

                    ParsedNode tableName = column.getChild("tableName", false);
                    ParsedNode schemaName = column.getChild("schemaName", false);
                    ParsedNode catalogName = column.getChild("catalogName", false);

                    if (tableName == null) {
                        actionNode.copyChildren("tableName", column);
                    }
                    if (schemaName == null) {
                        actionNode.copyChildren("schemaName", column);
                    }
                    if (catalogName == null) {
                        actionNode.copyChildren("catalogName", column);
                    }

                    ParsedNode relationNode = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", column);
                    if (relationNode != null) {
                        relationNode.rename("container");
                    }
                }


                actionNode.removeChildren("tableName");
                actionNode.removeChildren("schemaName");
                actionNode.removeChildren("catalogName");
            }
        };
    }
}

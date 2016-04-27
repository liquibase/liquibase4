package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ColumnReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

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
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(DropColumnsAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"dropColumn"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode columnNode = convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);
                        if (columnNode != null) {
                            columnNode.moveTo(actionNode.getChild("columns", true));
                        }

                        //convert nested column nodes
                        ParsedNode relationNode = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        for (ParsedNode column : actionNode.getChildren("column", false)) {
                            column.moveTo(actionNode.getChild("columns", true));
                            if (relationNode != null) {
                                relationNode.copyTo(column);
                            }
                        }

                        actionNode.removeChildren("relation");
                    }
                }
        };
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Column;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.unprocessor.AbstractActionUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

/**
 * Makes a column an auto-increment AKA identity column.
 */
public class AddAutoIncrementAction extends AbstractAction {

    public ColumnReference column;
    public DataType dataType;
    public Column.AutoIncrementDetails autoIncrementDetails;

    public AddAutoIncrementAction() {
    }

    public AddAutoIncrementAction(ColumnReference column, DataType dataType, Column.AutoIncrementDetails autoIncrementDetails) {
        this.column = column;
        this.dataType = dataType;
        this.autoIncrementDetails = autoIncrementDetails;
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(AddAutoIncrementAction.class) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                actionNode.renameChildren("name", "columnName");
                convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);

                actionNode.renameChildren("columnDataType", "dataType");

                ParsedNode autoIncrementDetails = actionNode.getChild("autoIncrementDetails", true);
                actionNode.moveChildren("incrementBy", autoIncrementDetails);
                actionNode.moveChildren("startWith", autoIncrementDetails);
            }
        };
    }

//    @Override
//    public ParsedNodeUnprocessor createUnprocessor() {
//        return new AbstractActionUnprocessor(AddAutoIncrementAction.class) {
//            @Override
//            protected void unprocessAction(ParsedNode actionNode, Scope scope) throws ParseException {
//                ParsedNode column = actionNode.getChild("column", false);
//                if (column != null) {
//                    column.renameChildren("name", "columnName");
//                    column.moveChildren("columnName", actionNode);
//
//                    ParsedNode tableNode = column.getChild("container", false);
//                    if (tableNode != null) {
//                        tableNode.renameChildren("name", "tableName");
//                        tableNode.moveChildren("tableName", actionNode);
//                    }
//                }
//            }
//        };
//    }
}

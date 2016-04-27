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

/**
 * Makes a column an auto-increment AKA identity column.
 */
public class AddAutoIncrementAction extends AbstractAction {

    public ColumnReference column;
    public DataType dataType;
    public Column.AutoIncrementInformation autoIncrementInformation;

    public AddAutoIncrementAction() {
    }

    public AddAutoIncrementAction(ColumnReference column, DataType dataType, Column.AutoIncrementInformation autoIncrementInformation) {
        this.column = column;
        this.dataType = dataType;
        this.autoIncrementInformation = autoIncrementInformation;
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(AddAutoIncrementAction.class) {
                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);

                        actionNode.renameChildren("columnDataType", "dataType");

                        ParsedNode autoIncrementInformation = actionNode.getChild("autoIncrementInformation", true);
                        actionNode.moveChildren("incrementBy", autoIncrementInformation);
                        actionNode.moveChildren("startWith", autoIncrementInformation);
                    }
                }
        };
    }
}

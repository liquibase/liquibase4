package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Sets the default value on an existing column. To drop the default value, set the value to null;
 */
public class AlterDefaultValueAction extends AbstractAction {
    public ColumnReference column;
    public DataType columnDataType;
    public Object defaultValue;

    public AlterDefaultValueAction() {
    }

    public AlterDefaultValueAction(ColumnReference column, Object defaultValue, DataType columnDataType) {
        this.column = column;
        this.defaultValue = defaultValue;
        this.columnDataType = columnDataType;
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AlterDefaultValuePreprocessor();
    }

    ;

    public static class AlterDefaultValuePreprocessor extends AbstractActionPreprocessor {

        public AlterDefaultValuePreprocessor() {
            super(AlterDefaultValueAction.class);
        }

        @Override
        protected String[] getAliases() {
            return new String[]{"addDefaultValue", "dropDefaultValue"};
        }

        @Override
        protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
            convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);
            convertValueOptions("defaultValue", actionNode);
        }

        @Override
        protected void processRenamedNode(String originalName, ParsedNode node) throws ParseException {
            if (originalName.equals("addDefaultValue")) {
                if (node.getChild("defaultValue", false) == null) {
                    throw new ParseException("No default value specified", node);
                }
            } else if (originalName.equals("dropDefaultValue")) {
                if (node.getChild("defaultValue", false) != null) {
                    throw new ParseException("Cannot specify a default value for dropDefaultValue", node);
                }
            }
        }
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

public class AddLookupTableAction extends AbstractAction {
    public ColumnReference existingColumn;
    public ColumnReference newColumn;
    public DataType newColumnDataType;
    public String foreignKeyName;
    public String primaryKeyName;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(AddLookupTableAction.class) {
                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode existingColumn = convertToColumnReferenceNode("existingTableCatalogName", "existingTableSchemaName", "existingTableName", "existingColumnName", actionNode);
                        if (existingColumn != null) {
                            existingColumn.rename("existingColumn");
                        }

                        ParsedNode newColumn = convertToColumnReferenceNode("newTableCatalogName", "newTableSchemaName", "newTableName", "newColumnName", actionNode);
                        if (newColumn != null) {
                            newColumn.rename("newColumn");
                        }

                        actionNode.renameChildren("constraintName", "foreignKeyName");
                    }
                }
        };
    };
}

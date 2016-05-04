package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.core.Column;
import liquibase.item.core.Table;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Sets remarks/comments on a database object.
 * Setting remarks as "null" should clear out existing remarks.
 * There is no SQL-standard for setting remarks, so each database implementation must implement it's own logic class or setting remarks will not be supported.
 * Logic implementations can be a single class to support objects on any type, or separate logic classes for each type, depending on what works best.
 */
public class AlterRemarksAction extends AbstractAction {
    /**
     * The object to set the remarks on
     */
    public DatabaseObjectReference object;

    public String remarks;

    public AlterRemarksAction() {
    }

    public AlterRemarksAction(DatabaseObjectReference object, String remarks) {
        this.object = object;
        this.remarks = remarks;
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(AlterRemarksAction.class) {
            @Override
            protected String[] getAliases() {
                return new String[]{"setTableRemarks", "setColumnRemarks"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode columnNode = actionNode.getChild("columnName", false);
                ParsedNode objectNode;
                if (columnNode != null) {
                    objectNode = convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);
                    objectNode.addChild("type").setValue(Column.class.getName());
                } else {
                    objectNode = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                    objectNode.addChild("type").setValue(Table.class.getName());
                }
                objectNode.rename("object");
            }
        };
    }
}

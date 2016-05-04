package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.PrimaryKey;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds primary keys to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are keys added to multiple tables.
 */
public class AddPrimaryKeysAction extends AbstractAction {

    public List<PrimaryKey> primaryKeys = new ArrayList<>();

    public AddPrimaryKeysAction() {
    }

    public AddPrimaryKeysAction(PrimaryKey... primaryKeys) {
        if (primaryKeys != null) {
            this.primaryKeys.addAll(Arrays.asList(primaryKeys));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(AddPrimaryKeysAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"addPrimaryKey"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                if (table != null) {
                    ParsedNode pk = actionNode.addChild("primaryKey");
                    table.moveTo(pk);

                    actionNode.moveChildren("tablespace", pk);
                    actionNode.moveChildren("constraintName", pk);
                    pk.renameChildren("constraintName", "name");

                    ParsedNode columns = pk.getChild("columns", true);
                    ParsedNode columnNames = actionNode.getChild("columnNames", false);
                    if (columnNames != null && columnNames.value != null) {
                        for (String name : StringUtil.splitAndTrim(columnNames.getValue("", String.class), ",")) {
                            ParsedNode pkColumn = columns.addChild("primaryKeyColumn");
                            pkColumn.addChild("name").setValue(name);
                        }
                        columnNames.remove();
                    }

//                            actionNode.moveChildren("forIndexCatalogName", pk);
//                            actionNode.moveChildren("forIndexSchemaName", pk);
//                            pk.renameChildren("forIndexSchemaName", );
//                            actionNode.moveChildren("clustered", pk);

                }

                actionNode.moveChildren("primaryKey", actionNode.getChild("primaryKeys", true));
            }
        };
    }
}

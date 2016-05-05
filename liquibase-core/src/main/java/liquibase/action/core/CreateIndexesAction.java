package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Index;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Add indexes to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are indexes added to multiple tables.
 */
public class CreateIndexesAction extends AbstractAction {

    public List<Index> indexes = new ArrayList<>();

    public CreateIndexesAction() {
    }

    public CreateIndexesAction(Index... indexes) {
        if (indexes != null) {
            this.indexes.addAll(Arrays.asList(indexes));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(CreateIndexesAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"createIndex"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                if (table != null) {
                    ParsedNode index = actionNode.addChild("index");

                    table.moveTo(index);

                    actionNode.renameChildren("indexName", "name");
                    actionNode.moveChildren("name", index);

                    actionNode.moveChildren("tablespace", index);
                    actionNode.moveChildren("clustered", index);
                    actionNode.moveChildren("unique", index);

                    actionNode.removeChildren("associatedWith");

                    ParsedNode columns = index.getChild("columns", true);
                    for (ParsedNode column : actionNode.getChildren("column", false)) {
                        column.rename("indexedColumn");
                        column.moveTo(columns);
                    }

                }
                actionNode.moveChildren("index", actionNode.getChild("indexes", true));
            }
        };
    }
}

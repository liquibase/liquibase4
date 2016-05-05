package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.IndexReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drops existing indexes.
 */
public class DropIndexesAction extends AbstractAction {

    public List<IndexReference> indexes = new ArrayList<>();

    public DropIndexesAction() {
    }

    public DropIndexesAction(IndexReference... indexes) {
        if (indexes != null) {
            this.indexes.addAll(Arrays.asList(indexes));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropIndexesAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropIndex"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                convertToIndexReferenceNode("catalogName", "schemaName", "tableName", "indexName", actionNode);
                ParsedNode indexes = actionNode.getChild("indexes", true);
                actionNode.moveChildren("index", indexes);

                actionNode.removeChildren("associatedWith");
            }
        };
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.preprocessor.core.changelog.ChangeSetPreprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drop existing table(s)
 */
public class DropTablesAction extends AbstractAction {
    public List<RelationReference> tables = new ArrayList<>();
    public Boolean cascadeConstraints;

    public DropTablesAction() {
    }

    public DropTablesAction(RelationReference... tables) {
        if (tables != null) {
            this.tables.addAll(Arrays.asList(tables));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(DropTablesAction.class) {

                    @Override
                    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
                        return CollectionUtil.union(Class.class, super.mustBeBefore(), ChangeSetPreprocessor.class);
                    }

                    @Override
                    protected String[] getAliases() {
                        return new String[]{"dropTable"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        actionNode.moveChildren("relation", actionNode.getChild("tables", true));
                    }
                }
        };
    }
}
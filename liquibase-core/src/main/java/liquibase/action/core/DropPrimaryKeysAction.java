package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.PrimaryKeyReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.List;

public class DropPrimaryKeysAction extends AbstractAction {

    public List<PrimaryKeyReference> primaryKeys = new ArrayList<>();

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(DropPrimaryKeysAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"dropPrimaryKey"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        ParsedNode constraintName = actionNode.getChild("constraintName", false);

                        if (table != null || constraintName != null) {
                            ParsedNode pk = actionNode.addChild("primaryKey");
                            if (table != null) {
                                table.rename("container").moveTo(pk);
                            }
                            if (constraintName != null) {
                                constraintName.rename("name").moveTo(pk);
                            }
                        }

                        actionNode.moveChildren("primaryKey", actionNode.getChild("primaryKeys", true));
                    }
                }
        };
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ForeignKeyReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Drops an existing foreign key.
 */
public class DropForeignKeysAction extends AbstractAction {

    public List<ForeignKeyReference> foreignKeys = new ArrayList<>();

    public DropForeignKeysAction() {
    }

    public DropForeignKeysAction(ForeignKeyReference... foreignKeys) {
        if (foreignKeys != null) {
            this.foreignKeys.addAll(Arrays.asList(foreignKeys));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(DropForeignKeysAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"dropForeignKeyConstraint"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode tableNode = convertToRelationReferenceNode("baseTableCatalogName", "baseTableSchemaName", "baseTableName", actionNode);
                        ParsedNode constraintName = actionNode.getChild("constraintName", false);
                        if (tableNode != null || constraintName != null) {
                            ParsedNode foreignKey = actionNode.addChild("foreignKey");
                            if (constraintName != null) {
                                constraintName.rename("name").moveTo(foreignKey);
                            }
                            if (tableNode != null) {
                                tableNode.rename("container").moveTo(foreignKey);
                            }
                        }

                        actionNode.moveChildren("foreignKey", actionNode.getChild("foreignKeys", true));
                    }
                }
        };
    }
}

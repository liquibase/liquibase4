package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.UniqueConstraint;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds unique constraints to a database.
 * {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are constraints added to multiple tables.
 */
public class AddUniqueConstraintsAction extends AbstractAction {

    public List<UniqueConstraint> uniqueConstraints = new ArrayList<>();

    public AddUniqueConstraintsAction() {

    }

    public AddUniqueConstraintsAction(UniqueConstraint... uniqueConstraints) {
        if (uniqueConstraints != null) {
            this.uniqueConstraints.addAll(Arrays.asList(uniqueConstraints));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(AddUniqueConstraintsAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"addUniqueConstraint"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                        if (table != null) {
                            ParsedNode uniqueConstraint = actionNode.addChild("uniqueConstraint");
                            table.moveTo(uniqueConstraint);
                            actionNode.moveChildren("disabled", uniqueConstraint);
                            actionNode.moveChildren("deferrable", uniqueConstraint);
                            actionNode.moveChildren("initiallyDeferred", uniqueConstraint);
                            actionNode.moveChildren("constraintName", uniqueConstraint);
                            uniqueConstraint.renameChildren("constraintName", "name");

                            String columnNames = actionNode.getChildValue("columnNames", String.class, true);
                            if (columnNames != null) {
                                uniqueConstraint.addChild("columns").setValue(new ArrayList<>(StringUtil.splitAndTrim(columnNames, ",")));
                            }
                        }
                        actionNode.moveChildren("uniqueConstraint", actionNode.getChild("uniqueConstraints", true));
                    }
                }
        };
    }
}

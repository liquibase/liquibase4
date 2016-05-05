package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.UniqueConstraintReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DropUniqueConstraintsAction extends AbstractAction {

    public List<UniqueConstraintReference> constraints = new ArrayList<>();

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropUniqueConstraintsAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropUniqueConstraint"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);

                actionNode.renameChildren("constraintName", "name");
                ParsedNode name = actionNode.getChild("name", false);

                if (table != null || name != null) {
                    ParsedNode constraint = actionNode.addChild("uniqueConstraint");

                    if (table != null) {
                        table.rename("container").moveTo(constraint);
                    }
                    if (name != null) {
                        name.moveTo(constraint);
                    }

                    ParsedNode uniqueColumns = actionNode.getChild("uniqueColumns", false);
                    if (uniqueColumns != null && uniqueColumns.getValue() != null) {
                        uniqueColumns.setValue(StringUtil.splitAndTrim(uniqueColumns.getValue(null, String.class), ","));
                        uniqueColumns.rename("columns");
                        uniqueColumns.moveTo(constraint);
                    }
                }

                actionNode.moveChildren("uniqueConstraint", actionNode.getChild("constraints", true));
            }
        };
    }
}

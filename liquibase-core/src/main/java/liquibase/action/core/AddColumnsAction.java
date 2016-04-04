package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Column;
import liquibase.item.core.ForeignKey;
import liquibase.item.core.PrimaryKey;
import liquibase.item.core.UniqueConstraint;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds columns to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are columns added to multiple tables.
 * For performance and/or necessity, primary keys, unique constraints and foreign keys can be added along with the columns.
 */
public class AddColumnsAction extends AbstractAction {

    public PrimaryKey primaryKey;
    public List<Column> columns = new ArrayList<>();
    public List<UniqueConstraint> uniqueConstraints = new ArrayList<>();
    public List<ForeignKey> foreignKeys = new ArrayList<>();

    public AddColumnsAction() {
    }

    public AddColumnsAction(Column... columns) {
        if (columns != null) {
            this.columns = new ArrayList<>(Arrays.asList(columns));
        }
    }

    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(AddColumnsAction.class) {
                    @Override
                    public String[] getAliases() {
                        return new String[]{"addColumn", "addColumns"};
                    }

                    /**
                     * <ul>
                     *  <li>Creates a "columns" node and moves all addColumns.column nodes to it</li>
                     *  <li>Copies a tableName value from "addColumns" to "column" nodes</li>
                     *  <li>Creates "column.relation" node</li>
                     * </ul>
                     */
                    @Override
                    protected void processActionNode(ParsedNode actionNode) throws ParseException {
                        String tableName = actionNode.getChildValue("tableName", String.class, true);

                        ParsedNode columns = actionNode.getChild("columns", true);
                        actionNode.moveChildren("column", columns);

                        for (ParsedNode column : actionNode.getChildren("column", true)) {
                            ParsedNode relation = column.addChild("relation");
                            relation.addChild("name").setValue(tableName);
                        }

                        super.processActionNode(actionNode);
                    }
                }
        };
    }
}

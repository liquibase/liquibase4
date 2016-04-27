package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ColumnReference;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

/**
 * Adds or removes a not null constraint to an existing column based on the {@link #nullable} value.
 * If {@link #valueForExistingNulls} is not null, all null values for the column are updated to that value before the constraint is applied.
 */
public class AlterNullableAction extends AbstractAction {

    public Boolean nullable;
    public ColumnReference column;
    public String constraintName;
    public DataType columnDataType;
    public Object valueForExistingNulls;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(AlterNullableAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"addNotNullConstraint", "dropNotNullConstraint"};
                    }

                    @Override
                    protected void processRenamedNode(String originalName, ParsedNode node) {
                        if (originalName.equals("addNotNullConstraint")) {
                            node.addChild("nullable").setValue(false);
                        }
                        if (originalName.equals("dropNotNullConstraint")) {
                            node.addChild("nullable").setValue(true);
                        }
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode);
                        actionNode.renameChildren("defaultNullValue", "valueForExistingNulls");
                    }
                }
        };
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.FunctionCall;
import liquibase.item.core.RowData;
import liquibase.item.function.SequenceCurrentValueFunction;
import liquibase.item.function.SequenceNextValueFunction;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.*;


/**
 * Insert data into a database.
 * Data can be conditionally inserted or updated using the {@link #columnsForUpdateCheck}
 */
public class InsertDataAction extends AbstractAction {

    public String dbms;
    public List<RowData> data = new ArrayList<>();

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-databse compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();

    public InsertDataAction() {
    }

    public InsertDataAction(List<RowData> data) {
        this.data = data;
    }

    public InsertDataAction(RowData... data) {
        this.data = Arrays.asList(CollectionUtil.createIfNull(data));
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(InsertDataAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"insert"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode data = actionNode.getChild("data", true);

                ParsedNode relation = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                if (relation != null) {
                    ParsedNode rowData = null;
                    ParsedNode dataNode = null;
                    for (ParsedNode column : actionNode.getChildren("column", false)) {
                        if (rowData == null) {
                            rowData = data.addChild("rowData");
                            relation.moveTo(rowData);
                            dataNode = rowData.addChild("data");
                        }

                        column.rename("cell").moveTo(dataNode);
                        column.renameChildren("name", "columnName");

                        convertValueOptions("value", column);
                    }
                }

            }
        };
    }
}

package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RowData;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

import java.util.*;


/**
 * Insert data into a database.
 * Data can be conditionally inserted or updated using the {@link #columnsForUpdateCheck}
 */
public class InsertDataAction extends AbstractAction {

    public String dbms;
    public List<RowData> rows = new ArrayList<>();

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-databse compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();

    public InsertDataAction() {
    }

    public InsertDataAction(List<RowData> rows) {
        this.rows = rows;
    }

    public InsertDataAction(RowData... rows) {
        this.rows = Arrays.asList(CollectionUtil.createIfNull(rows));
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
                ParsedNode generatedRowData = null;

                //bundle top-level columns into a single rowData
                for (ParsedNode column : actionNode.getChildren("column", false)) {
                    if (generatedRowData == null) {
                        generatedRowData = actionNode.addChild("row");
                    }

                    column.moveTo(generatedRowData);
                }

                ParsedNode relation = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                for (ParsedNode row : actionNode.getChildren("row", false)) {
                    convertToRelationReferenceNode("catalogName", "schemaName", "tableName", row); //can set relation per row
                    if (!row.hasChild("relation") && relation != null) {
                        relation.copyTo(row);
                    }
                }

                if (relation != null) {
                    relation.remove();
                }
            }
        };
    }
}

package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.preprocessor.core.changelog.RelativeToChangelogFilePreprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Loads data from a file.
 */
public class LoadDataAction extends AbstractAction {

    public RelationReference table;
    public List<LoadDataColumn> columns = new ArrayList<>();

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-database compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();
    public String path;
    public String commentLineStart;
    public String encoding;
    public String quoteChar;
    public String separator;
    public Boolean onlyUpdate;

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(LoadDataAction.class) {

            @Override
            public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
                return CollectionUtil.union(Class.class, super.mustBeBefore(), RelativeToChangelogFilePreprocessor.class);
            }

            @Override
            protected String[] getAliases() {
                return new String[]{"loadUpdateData"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode table = convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);
                if (table != null) {
                    table.rename("table");
                }
                actionNode.renameChildren("file", "path");
                actionNode.renameChildren("quotchar", "quoteChar");
                actionNode.renameChildren("commentLineStartsWith", "commentLineStart");

                ParsedNode columns = actionNode.getChild("columns", true);
                actionNode.moveChildren("column", columns);

                for (ParsedNode column : columns.getChildren()) {
                    column.renameChildren("position", "index");
                    column.renameChildren("name", "header");
                }

                ParsedNode primaryKey = actionNode.getChild("primaryKey", false);
                if (primaryKey != null && primaryKey.value != null) {
                    primaryKey.rename("columnsForUpdateCheck");
                    primaryKey.setValue(new ArrayList<>(Collections.singletonList(primaryKey.getValue(null, String.class))));
                }
            }
        };
    }

    public static class LoadDataColumn extends AbstractExtensibleObject {

        public Integer index;
        public String header;
        public String columnName;
        public String type;
        public DataType dataType;
        public Boolean skip;
        public Object defaultValue;

        public LoadDataColumn() {
        }

        public LoadDataColumn(String header, DataType dataType) {
            this.header = header;
            this.dataType = dataType;
        }

        public LoadDataColumn(int index, DataType dataType) {
            this.index = index;
            this.dataType = dataType;
        }
    }

}

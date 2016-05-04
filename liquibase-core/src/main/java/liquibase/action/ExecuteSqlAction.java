package liquibase.action;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.ObjectUtil;
import liquibase.util.SqlParser;
import liquibase.util.StringClauses;

/**
 * Describes a SQL-based action that is neither a query nor an update of existing data.
 * Those types of queries should use {@link QuerySqlAction} or {@link UpdateSqlAction}.
 */
public class ExecuteSqlAction extends AbstractSqlAction {

    public ExecuteSqlAction() {
    }

    public ExecuteSqlAction(String sql) {
        super(sql);
    }

    public ExecuteSqlAction(StringClauses sql) {
        this(sql.toString());
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(ExecuteSqlAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"sql"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                actionNode.removeChildren("comment");

                String sql = actionNode.getValue(null, String.class);

                if (sql != null) {
                    boolean splitStatements = ObjectUtil.defaultIfNull(actionNode.getChildValue("splitStatements", Boolean.class, true), true);
                    boolean stripComments = ObjectUtil.defaultIfNull(actionNode.getChildValue("stripComments", Boolean.class, true), false);
                    ParsedNode endDelimiterNode = actionNode.getChild("endDelimiter", false);

                    String endDelimiter = null;
                    if (endDelimiterNode == null || endDelimiterNode.value == null) {
                        endDelimiter = ";";
                    }

                    if (splitStatements) {
                        StringClauses parsedSql = SqlParser.parse(sql, stripComments, true);
                        for (StringClauses clauses : parsedSql.split(endDelimiter)) {
                            ParsedNode executeSqlNode = actionNode.addChild("executeSql");
                            executeSqlNode.addChild("sql").setValue(clauses);
                            if (endDelimiterNode != null) {
                                endDelimiterNode.copyTo(executeSqlNode);
                            }
                        }
                    }
                }

            }
        };
    }
}

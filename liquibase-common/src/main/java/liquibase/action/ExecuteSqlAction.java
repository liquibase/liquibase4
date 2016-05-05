package liquibase.action;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.ObjectUtil;
import liquibase.util.SqlParser;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

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

                if (actionNode.getValue() != null) {
                    actionNode.moveValue(actionNode.addChild("sql"));
                }

                ParsedNode dbms = actionNode.getChild("dbms", false);
                if (dbms != null) {
                    dbms.rename("dbmsFilters").setValue(StringUtil.splitAndTrim(dbms.getValue(null, String.class), ","));
                }
            }
        };
    }
}

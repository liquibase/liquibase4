package liquibase.action;

import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.util.StringClauses;

/**
 * Describes a SQL-based query action.
 */
public class QuerySqlAction extends AbstractSqlAction implements QueryAction {

    public QuerySqlAction() {
    }

    public QuerySqlAction(String sql) {
        super(sql);
    }

    public QuerySqlAction(StringClauses sql) {
        super(sql);
    }

    @Override
    public String getName() {
        return "query";
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new SqlPreprocessor(getClass()) {};
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new SqlUnprocessor(getClass()) {};
    }
}

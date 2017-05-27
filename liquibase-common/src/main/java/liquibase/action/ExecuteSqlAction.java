package liquibase.action;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.unprocessor.AbstractActionUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
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
    public String getName() {
        return "sql";
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

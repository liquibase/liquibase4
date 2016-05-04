package liquibase.action;

import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard base class for sql-based actions.
 * Normally subclass from the more specific {@link UpdateSqlAction}, {@link QuerySqlAction} and {@link ExecuteSqlAction}.
 */
public abstract class AbstractSqlAction extends AbstractAction {

    public StringClauses sql;
    public String endDelimiter;
    public Boolean splitStatements;
    public Boolean stripComments;
    public List<String> dbmsFilters = new ArrayList<>();

    public AbstractSqlAction() {
    }

    public AbstractSqlAction(String sql) {
        this.sql = new StringClauses().append(sql);
    }

    public AbstractSqlAction(StringClauses sql) {
        this.sql = sql;
    }

    @Override
    public String describe() {
        return sql + ObjectUtil.defaultIfNull(endDelimiter, "");
    }
}

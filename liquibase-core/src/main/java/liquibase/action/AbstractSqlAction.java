package liquibase.action;

import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

/**
 * Standard base class for sql-based actions.
 * Normally subclass from the more specific {@link UpdateSqlAction}, {@link QuerySqlAction} and {@link ExecuteSqlAction}.
 */
public abstract class AbstractSqlAction extends AbstractAction {

    public StringClauses sql;
    public String endDelimiter;
    public String dbms;

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

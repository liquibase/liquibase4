package liquibase.action;

import liquibase.util.StringClauses;

/**
 * Describes a SQL-based action that updates data.
 */
public class UpdateSqlAction extends AbstractSqlAction implements UpdateAction {

    public UpdateSqlAction(String sql) {
        super(sql);
    }

    public UpdateSqlAction(StringClauses sql) {
        super(sql);
    }
}

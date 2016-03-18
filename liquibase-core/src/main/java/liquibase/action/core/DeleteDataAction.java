package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;
import liquibase.util.StringClauses;

public class DeleteDataAction extends AbstractAction {

    public RelationReference relation;

    /**
     * Default where clauses are " AND " joined.
     * This can be replaced if you need a different standard operator, or just add a single StringClauses element with a different operator joining it's own clauses.
     */
    public StringClauses where = new StringClauses(" AND ");

}

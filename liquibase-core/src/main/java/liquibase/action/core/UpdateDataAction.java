package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.action.AbstractAction;
import liquibase.action.UpdateAction;
import liquibase.item.core.RelationReference;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Updates existing data.
 * For mixed insert/updates, use {@link InsertDataAction} with {@link InsertDataAction#columnsForUpdateCheck}.
 */
public class UpdateDataAction extends AbstractAction implements UpdateAction {
    public RelationReference relation;
    public List<UpdatedColumn> columns = new ArrayList<>();

    /**
     * Default where clauses are " AND " joined.
     * This can be replaced if you need a different standard operator, or just add a single StringClauses element with a different operator joining it's own clauses.
     */
    public StringClauses where = new StringClauses(" AND ");


    public UpdateDataAction() {
    }

    public UpdateDataAction(RelationReference relation, StringClauses where, UpdatedColumn... columns) {
        this.relation = relation;
        if (where != null) {
            this.where = where;
        }
        this.columns.addAll(Arrays.asList(CollectionUtil.createIfNull(columns)));
    }

    public static class UpdatedColumn extends AbstractExtensibleObject {
        public String name;
        public Object value;

        public UpdatedColumn() {
        }

        public UpdatedColumn(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }


}

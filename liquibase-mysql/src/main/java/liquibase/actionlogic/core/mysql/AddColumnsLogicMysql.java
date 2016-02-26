package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.core.AddColumnsAction;
import liquibase.actionlogic.core.AddColumnsLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.Column;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.List;

public class AddColumnsLogicMysql extends AddColumnsLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(AddColumnsAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);

        if (!errors.hasErrors()) {
            for (Column column : action.columns)
                if (ObjectUtil.defaultIfEmpty(column.isAutoIncrement(), false) && !isPrimaryKey(column, action)) {
                    errors.addUnsupportedError("non-primary key auto-increment columns");
                }
        }
        return errors;
    }

    @Override
    protected void createAddColumnsActions(AddColumnsAction action, Scope scope, List<Action> actions) {
        super.createAddColumnsActions(action, scope, actions);
    //todo: support multiple columns in a single alter table
//        String alterTable = generateSingleColumBaseSQL(columns.get(0), database);
//        for (int i = 0; i < columns.size(); i++) {
//            alterTable += getColumnClause(columns.get(i), database);
//            if (i < columns.size() - 1) {
//                alterTable += ",";
//            }
//        }
    }

    @Override
    protected StringClauses getColumnClause(Column column, AddColumnsAction action, Scope scope) {
        return super.getColumnClause(column, action, scope)
                .insertAfter(Clauses.primaryKey, column.remarks == null ? null : "COMMENT " + scope.getDatabase().quoteString(column.remarks, scope));
    }

    public boolean requiresDefiningColumnsAsNull() {
        return true;
    }
}

package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.AddColumnsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.core.AddColumnsLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.Column;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

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
    public ActionResult createAddColumnAction(AddColumnsAction action, Scope scope) throws ActionPerformException {
        return super.createAddColumnAction(action, scope);
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
                .insertAfter(Clauses.primaryKey, column.remarks == null ? null : "COMMENT '" + scope.getDatabase().escapeString(column.remarks) + "'");
    }
}

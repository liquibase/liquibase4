package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.RenameColumnAction;
import liquibase.actionlogic.core.RenameColumnLogic;
import liquibase.actionlogic.core.RenameTableLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.Column;
import liquibase.util.StringClauses;

public class RenameColumnLogicMysql extends RenameColumnLogic {

    public enum Clauses {
        columnDefinition
    }

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(RenameColumnAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("columnDefinition");
    }

    @Override
    protected StringClauses generateSql(RenameColumnAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("ALTER TABLE")
                .append(database.quoteObjectName(action.relation, scope))
                .append("CHANGE")
                .append("COLUMN")
                .append(database.quoteObjectName(action.oldName, Column.class, scope))
                .append(database.quoteObjectName(action.newName, Column.class, scope))
                .append(Clauses.columnDefinition, action.columnDefinition);
    }

}

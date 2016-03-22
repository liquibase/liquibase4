package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.core.MergeColumnsAction;
import liquibase.actionlogic.core.MergeColumnsLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.FunctionCall;
import liquibase.item.core.Column;

public class MergeColumnsLogicMysql extends MergeColumnsLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Object getConcatValue(String column1Name, String joinString, String column2Name, MergeColumnsAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new FunctionCall("CONCAT("
                + database.quoteObjectName(column1Name, Column.class, scope)
                + ", "
                + database.quoteString(joinString, scope)
                + ", "
                + database.quoteObjectName(column2Name, Column.class, scope)
                + ")"
        );
    }
}

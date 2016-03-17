package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.core.AlterColumnAction;
import liquibase.action.core.AlterNullableAction;
import liquibase.actionlogic.core.AlterNullableLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.datatype.DataTypeLogicFactory;

public class AlterNullableLogicMysql extends AlterNullableLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ValidationErrors validate(AlterNullableAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("columnDataType");
    }

    @Override
    protected Action createAction(AlterNullableAction action, Scope scope) {
        AlterColumnAction alterAction = (AlterColumnAction) super.createAction(action, scope);
        alterAction.newDefinition.prepend(scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(action.columnDataType, scope).toSql(action.columnDataType, scope));
        return alterAction;
    }
}

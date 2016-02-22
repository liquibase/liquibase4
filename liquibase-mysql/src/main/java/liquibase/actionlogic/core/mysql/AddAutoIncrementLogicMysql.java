package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.AddAutoIncrementAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.core.AddAutoIncrementLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.structure.core.Column;
import liquibase.util.StringClauses;

public class AddAutoIncrementLogicMysql extends AddAutoIncrementLogic {

    @Override
    public ValidationErrors validate(AddAutoIncrementAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("dataType")
                .checkUnsupportedFields("autoIncrementInformation.incrementBy");
    }

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    public ActionResult execute(AddAutoIncrementAction action, Scope scope) throws ActionPerformException {
        DelegateResult delegate = (DelegateResult) super.execute(action, scope);
        if (action.autoIncrementInformation != null && action.autoIncrementInformation.startWith != null) {
            delegate.addActions(new AlterTableAction(action.column.container, new StringClauses().append("AUTO_INCREMENT="+action.autoIncrementInformation.startWith)));
        }
        return delegate;
    }

    @Override
    public StringClauses generateAutoIncrementClause(Column.AutoIncrementInformation autoIncrementInformation) {
        return new StringClauses().append("AUTO_INCREMENT");
    }
}
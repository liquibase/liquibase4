package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropForeignKeyAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.ForeignKey;
import liquibase.util.StringClauses;

public class DropForeignKeyLogic extends AbstractSqlBuilderLogic<DropForeignKeyAction> {

    @Override
    protected Class<DropForeignKeyAction> getSupportedAction() {
        return DropForeignKeyAction.class;
    }

    @Override
    public ValidationErrors validate(DropForeignKeyAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("foreignKey", "foreignKey.name", "foreignKey.container");
    }

    @Override
    public ActionResult execute(DropForeignKeyAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new AlterTableAction(
                action.foreignKey.getRelation(),
                generateSql(action, scope)
        ));
    }

    @Override
    protected StringClauses generateSql(DropForeignKeyAction action, Scope scope) {
        Database database = scope.getDatabase();
        return new StringClauses()
                .append("DROP CONSTRAINT")
                .append(database.quoteObjectName(action.foreignKey.name, ForeignKey.class, scope));
    }
}

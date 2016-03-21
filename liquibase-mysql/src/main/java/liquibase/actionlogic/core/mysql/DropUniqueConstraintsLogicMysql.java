package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.AlterTableAction;
import liquibase.action.core.DropUniqueConstraintsAction;
import liquibase.actionlogic.core.DropUniqueConstraintsLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.item.core.UniqueConstraintReference;

public class DropUniqueConstraintsLogicMysql extends DropUniqueConstraintsLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Action generateAction(UniqueConstraintReference uq, DropUniqueConstraintsAction action, Scope scope) {
        AlterTableAction alterTableAction = (AlterTableAction) super.generateAction(uq, action, scope);
        alterTableAction.newDefinition.replace("CONSTRAINT", "KEY");
        return alterTableAction;
    }
}

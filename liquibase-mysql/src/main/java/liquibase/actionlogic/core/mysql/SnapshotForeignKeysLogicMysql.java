package liquibase.actionlogic.core.mysql;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.actionlogic.core.SnapshotForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.mysql.MysqlDatabase;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.ItemReference;
import liquibase.item.core.*;

public class SnapshotForeignKeysLogicMysql extends SnapshotForeignKeysLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return MysqlDatabase.class;
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        SelectDataAction query = new SelectDataAction("KEY_COL", new RelationReference(Table.class, "INFORMATION_SCHEMA", "KEY_COLUMN_USAGE"),
                new SelectDataAction.SelectedColumn("KEY_COL", "CONSTRAINT_SCHEMA", "FKTABLE_CAT"),
                new SelectDataAction.SelectedColumn("KEY_COL", "CONSTRAINT_NAME", "FK_NAME"),
                new SelectDataAction.SelectedColumn("KEY_COL", "REFERENCED_TABLE_SCHEMA", "PKTABLE_CAT"),
                new SelectDataAction.SelectedColumn("KEY_COL", "REFERENCED_TABLE_NAME", "PKTABLE_NAME"),
                new SelectDataAction.SelectedColumn("KEY_COL", "REFERENCED_COLUMN_NAME", "PKCOLUMN_NAME"),
                new SelectDataAction.SelectedColumn("KEY_COL", "TABLE_SCHEMA", "FKTABLE_CAT"),
                new SelectDataAction.SelectedColumn("KEY_COL", "TABLE_NAME", "FKTABLE_NAME"),
                new SelectDataAction.SelectedColumn("KEY_COL", "COLUMN_NAME", "FKCOLUMN_NAME"),
                new SelectDataAction.SelectedColumn("KEY_COL", "ORDINAL_POSITION", "KEY_SEQ"),
                new SelectDataAction.SelectedColumn("CON", "UPDATE_RULE", "UPDATE_RULE_STRING"),
                new SelectDataAction.SelectedColumn("CON", "DELETE_RULE", "DELETE_RULE_STRING")
        )
                .addJoin(new SelectDataAction.JoinedRelation(new RelationReference(Table.class, "INFORMATION_SCHEMA", "REFERENTIAL_CONSTRAINTS"), "CON", SelectDataAction.JoinType.inner)
                        .addOnClause("KEY_COL.CONSTRAINT_SCHEMA=CON.CONSTRAINT_SCHEMA")
                        .addOnClause("KEY_COL.CONSTRAINT_NAME=CON.CONSTRAINT_NAME")
                        .addOnClause("KEY_COL.TABLE_NAME=CON.TABLE_NAME"))
                .addWhere("KEY_COL.REFERENCED_COLUMN_NAME IS NOT NULL");

        if (relatedTo.instanceOf(ForeignKey.class)) {
            if (relatedTo.name == null) {
                ItemReference baseTable = ((ForeignKeyReference) relatedTo).container;
                query.addWhere("KEY_COL.TABLE_NAME=" + database.quoteString(baseTable.name, scope))
                        .addWhere("KEY_COL.TABLE_SCHEMA=" + database.quoteString(baseTable.container.name, scope));
            } else {
                query.addWhere("KEY_COL.CONSTRAINT_NAME=" + database.quoteString(relatedTo.name, scope))
                        .addWhere("KEY_COL.CONSTRAINT_SCHEMA=" + database.quoteString(relatedTo.container.container.name, scope));
            }
        } else if (relatedTo.instanceOf(Table.class)) {
            query.addWhere("KEY_COL.TABLE_NAME=" + database.quoteString(relatedTo.name, scope))
                    .addWhere("KEY_COL.TABLE_SCHEMA=" + database.quoteString(relatedTo.container.name, scope));
        } else if (relatedTo.instanceOf(Schema.class)) {
            query.addWhere("KEY_COL.CONSTRAINT_SCHEMA=" + database.quoteString(relatedTo.name, scope));
        } else {
            throw new ActionPerformException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }
        return query;
    }

    @Override
    protected ForeignKey convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        ForeignKey fk = super.convertToObject(object, relatedTo, originalAction, scope);

        String updateRule = row.get("UPDATE_RULE_STRING", String.class);
        if (updateRule != null) {
            switch (updateRule) {
                case "CASCADE":
                    fk.updateRule = ForeignKey.ReferentialAction.cascade;
                    break;
                case "SET NULL":
                    fk.updateRule = ForeignKey.ReferentialAction.setNull;
                    break;
                case "SET DEFAULT":
                    fk.updateRule = ForeignKey.ReferentialAction.setDefault;
                    break;
                case "RESTRICT":
                    fk.updateRule = ForeignKey.ReferentialAction.restrict;
                    break;
                case "NO ACTION":
                    fk.updateRule = ForeignKey.ReferentialAction.noAction;
                    break;
                default:
                    throw new ActionPerformException("Unknown update rule: " + updateRule);
            }
        }

        String deleteRule = row.get("DELETE_RULE_STRING", String.class);
        if (deleteRule != null) {
            switch (deleteRule) {
                case "CASCADE":
                    fk.deleteRule = ForeignKey.ReferentialAction.cascade;
                    break;
                case "SET NULL":
                    fk.deleteRule = ForeignKey.ReferentialAction.setNull;
                    break;
                case "SET DEFAULT":
                    fk.deleteRule = ForeignKey.ReferentialAction.setDefault;
                    break;
                case "RESTRICT":
                    fk.deleteRule = ForeignKey.ReferentialAction.restrict;
                    break;
                case "NO ACTION":
                    fk.deleteRule = ForeignKey.ReferentialAction.noAction;
                    break;
                default:
                    throw new ActionPerformException("Unknown delete rule: " + deleteRule);
            }
        }

        return fk;
    }
}

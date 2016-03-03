package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.core.SnapshotForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.ItemReference;
import liquibase.item.core.*;

public class SnapshotForeignKeysLogicH2 extends SnapshotForeignKeysLogic {


    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        SelectDataAction query = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "CROSS_REFERENCES"),
                new SelectDataAction.SelectedColumn(null, "PKTABLE_CATALOG", "PKTABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "PKTABLE_SCHEMA", "PKTABLE_SCHEM"),
                new SelectDataAction.SelectedColumn("PKTABLE_NAME"),
                new SelectDataAction.SelectedColumn("PKCOLUMN_NAME"),
                new SelectDataAction.SelectedColumn(null, "FKTABLE_CATALOG", "FKTABLE_CAT"),
                new SelectDataAction.SelectedColumn(null, "FKTABLE_SCHEMA", "FKTABLE_SCHEM"),
                new SelectDataAction.SelectedColumn("FKTABLE_NAME"),
                new SelectDataAction.SelectedColumn("FKCOLUMN_NAME"),
                new SelectDataAction.SelectedColumn(null, "ORDINAL_POSITION", "KEY_SEQ"),
                new SelectDataAction.SelectedColumn("UPDATE_RULE"),
                new SelectDataAction.SelectedColumn("DELETE_RULE"),
                new SelectDataAction.SelectedColumn("FK_NAME"),
                new SelectDataAction.SelectedColumn("PK_NAME"),
                new SelectDataAction.SelectedColumn("DEFERRABILITY"));

        if (relatedTo.instanceOf(ForeignKey.class)) {
            if (relatedTo.name == null) {
                ItemReference baseTable = ((ForeignKeyReference) relatedTo).container;
                query.addWhere("FKTABLE_SCHEMA=" + database.quoteString(baseTable.container.name, scope))
                        .addWhere("FKTABLE_NAME=" + database.quoteString(baseTable.name, scope));
            } else {
                query.addWhere("FK_NAME=" + database.quoteString(relatedTo.name, scope))
                        .addWhere("FKTABLE_SCHEMA=" + database.quoteString(((ForeignKeyReference) relatedTo).getSchema().name, scope));
            }
        } else if (relatedTo instanceof RelationReference) {
            query.addWhere("FKTABLE_SCHEMA=" + database.quoteString(relatedTo.container.name, scope))
                    .addWhere("FKTABLE_NAME=" + database.quoteString(relatedTo.name, scope));
        } else if (relatedTo.instanceOf(Schema.class)) {
            query.addWhere("FKTABLE_SCHEMA=" + database.quoteString(relatedTo.name, scope));
        } else {
            throw new ActionPerformException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        query.addOrder(
                new SelectDataAction.OrderedByColumn("PKTABLE_CAT"),
                new SelectDataAction.OrderedByColumn("PKTABLE_SCHEM"),
                new SelectDataAction.OrderedByColumn("PKTABLE_NAME"),
                new SelectDataAction.OrderedByColumn("FK_NAME"),
                new SelectDataAction.OrderedByColumn("KEY_SEQ"));

        return query;
    }

}

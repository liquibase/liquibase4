package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.core.SnapshotForeignKeysLogicJdbc;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringClauses;

public class SnapshotForeignKeysLogicH2 extends SnapshotForeignKeysLogicJdbc {


    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();
        StringClauses query = new StringClauses(" ").append("SELECT PKTABLE_CATALOG PKTABLE_CAT, " +
                "PKTABLE_SCHEMA PKTABLE_SCHEM, " +
                "PKTABLE_NAME PKTABLE_NAME, " +
                "PKCOLUMN_NAME, " +
                "FKTABLE_CATALOG FKTABLE_CAT, " +
                "FKTABLE_SCHEMA FKTABLE_SCHEM, " +
                "FKTABLE_NAME, " +
                "FKCOLUMN_NAME, " +
                "ORDINAL_POSITION KEY_SEQ, " +
                "UPDATE_RULE, " +
                "DELETE_RULE, " +
                "FK_NAME, " +
                "PK_NAME, " +
                "DEFERRABILITY " +
                "FROM INFORMATION_SCHEMA.CROSS_REFERENCES");
        if (relatedTo.instanceOf(ForeignKey.class)) {
            if (relatedTo.name== null) {
                ObjectReference baseTable = ((ForeignKey.ForeignKeyReference) relatedTo).container;
                query.append("WHERE FKTABLE_SCHEMA='" + database.escapeString(baseTable.container.name) + "'")
                        .append("AND FKTABLE_NAME='" + database.escapeString(baseTable.name) + "'");
            } else {
                query.append("WHERE FK_NAME='" + database.escapeString(relatedTo.name) + "'")
                        .append("AND FKTABLE_SCHEMA='" + database.escapeString(relatedTo.container.container.name) + "'");
            }
        } else if (relatedTo.instanceOf(Table.class)) {
            query.append("WHERE FKTABLE_SCHEMA='" + database.escapeString(relatedTo.container.name) + "'")
                    .append("AND FKTABLE_NAME='" + database.escapeString(relatedTo.name) + "'");
        } else if (relatedTo.instanceOf(Schema.class)) {
            query.append("WHERE FKTABLE_SCHEMA='" + database.escapeString(relatedTo.name) + "'");
        } else {
            throw new ActionPerformException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        query.append("ORDER BY PKTABLE_CAT, " +
                "PKTABLE_SCHEM, " +
                "PKTABLE_NAME, " +
                "FK_NAME, " +
                "KEY_SEQ");
        return new QuerySqlAction(query);
    }

}

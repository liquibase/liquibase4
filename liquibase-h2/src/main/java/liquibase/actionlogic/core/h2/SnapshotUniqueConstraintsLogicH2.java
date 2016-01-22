package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ObjectBasedQueryResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.actionlogic.core.SnapshotForeignKeysLogicJdbc;
import liquibase.actionlogic.core.SnapshotUniqueConstraintsLogicJdbc;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.StringClauses;
import org.apache.velocity.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class SnapshotUniqueConstraintsLogicH2 extends SnapshotUniqueConstraintsLogicJdbc {


    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        QuerySqlAction snapshotAction = (QuerySqlAction) super.createSnapshotAction(relatedTo, action, scope);

        snapshotAction.sql.replace(Clauses.columnList, "TC.CONSTRAINT_NAME, " +
                "TC.TABLE_CATALOG, " +
                "TC.TABLE_SCHEMA, " +
                "TC.TABLE_NAME, " +
                "FALSE AS IS_DEFERRABLE, " +
                "FALSE AS INITIALLY_DEFERRED, " +
                "COLUMN_LIST");
        snapshotAction.sql.replace("FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC", "FROM INFORMATION_SCHEMA.CONSTRAINTS TC");
        snapshotAction.sql.remove(Clauses.columnJoinClause);
        snapshotAction.sql.remove("ORDER BY ORDINAL_POSITION");

        return snapshotAction;
    }

    @Override
    protected LiquibaseObject convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        UniqueConstraint returnObject = (UniqueConstraint) super.convertToObject(object, relatedTo, originalAction, scope);

        returnObject.columns = Arrays.asList(StringUtils.split(((RowBasedQueryResult.Row) object).get("COLUMN_LIST", String.class), ","));

        return returnObject;
    }

    protected ActionResult.Modifier createModifier(ObjectReference relatedTo, SnapshotObjectsAction originalAction, final Scope scope) {
        return new SnapshotModifier(relatedTo, originalAction, scope);
    }
}

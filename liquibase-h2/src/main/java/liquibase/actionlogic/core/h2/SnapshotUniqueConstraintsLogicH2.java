package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.DelegateResult;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.actionlogic.core.SnapshotUniqueConstraintsLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.UniqueConstraint;
import org.apache.velocity.util.StringUtils;

import java.util.Arrays;

public class SnapshotUniqueConstraintsLogicH2 extends SnapshotUniqueConstraintsLogic {


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
    protected UniqueConstraint convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        UniqueConstraint returnObject = super.convertToObject(object, relatedTo, originalAction, scope);

        returnObject.columns = Arrays.asList(StringUtils.split(((RowBasedQueryResult.Row) object).get("COLUMN_LIST", String.class), ","));

        return returnObject;
    }

    protected DelegateResult.Modifier createModifier(ObjectReference relatedTo, SnapshotObjectsAction originalAction, final Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope);
    }
}

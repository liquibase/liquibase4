package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.StringClauses;
import liquibase.util.Validate;

import java.util.*;

public class SnapshotUniqueConstraintsLogic extends AbstractSnapshotDatabaseObjectsLogic<UniqueConstraint> {

    public enum Clauses {
        columnList,
        columnJoinClause, whereClauses,
    }

    @Override
    protected Class<UniqueConstraint> getTypeToSnapshot() {
        return UniqueConstraint.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                UniqueConstraint.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) throws ActionPerformException {
        Database database = scope.getDatabase();

        String catalogName = null;
        String schemaName = null;
        String tableName = null;
        String constraintName = null;
        if (relatedTo.instanceOf(Catalog.class)) {
            catalogName = relatedTo.name;
        } else if (relatedTo.instanceOf(Schema.class)) {
            List<String> names = relatedTo.asList(2);
            catalogName = names.get(0);
            schemaName = names.get(1);
        } else if (relatedTo.instanceOf(Table.class)) {
            List<String> names = relatedTo.asList(3);
            catalogName = names.get(0);
            schemaName = names.get(1);
            tableName = names.get(2);
        } else if (relatedTo.instanceOf(UniqueConstraint.class)) {
            List<String> names = relatedTo.asList(4);
            catalogName = names.get(0);
            schemaName = names.get(1);
            tableName = names.get(2);
            constraintName = names.get(3);
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        StringClauses query = new StringClauses(" ").append("SELECT")
                .append(Clauses.columnList, "TC.CONSTRAINT_NAME, " +
                                (database.supports(Catalog.class, scope) ? "TC.TABLE_CATALOG, " : "NULL AS TABLE_CATALOG, ") +
                                "TC.TABLE_SCHEMA, " +
                                "TC.TABLE_NAME, " +
                                (database.supports(Database.Feature.DEFERRABLE_CONSTRAINTS, scope) ? "TC.IS_DEFERRABLE, " : "NULL AS IS_DEFERRABLE, ") +
                                (database.supports(Database.Feature.DEFERRABLE_CONSTRAINTS, scope) ? "TC.INITIALLY_DEFERRED, " : "NULL AS INITIALLY_DEFERRED, ") +
                                "KCU.COLUMN_NAME"
                ).append("FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC")
                .append(Clauses.columnJoinClause, "JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU " +
                        "ON TC.CONSTRAINT_NAME=KCU.CONSTRAINT_NAME " +
                        (database.supports(Catalog.class, scope) ? "AND TC.TABLE_CATALOG=KCU.TABLE_CATALOG " : "") +
                        "AND TC.TABLE_SCHEMA=KCU.TABLE_SCHEMA " +
                        "AND TC.TABLE_NAME=KCU.TABLE_NAME");

        StringClauses whereClause = new StringClauses(" AND ");
        if (catalogName != null) {
            whereClause.append("TC.CONSTRAINT_CATALOG=" + database.quoteString(catalogName, scope));
        }
        if (schemaName != null) {
            whereClause.append("TC.CONSTRAINT_SCHEMA=" + database.quoteString(schemaName, scope));
        }
        if (tableName != null) {
            whereClause.append("TC.TABLE_NAME=" + database.quoteString(tableName, scope));
        }
        if (constraintName != null) {
            whereClause.append("TC.CONSTRAINT_NAME=" + database.quoteString(constraintName, scope));
        }

        if (!whereClause.isEmpty()) {
            query.append("WHERE").append(Clauses.whereClauses, whereClause);
        }

        query.append("ORDER BY ORDINAL_POSITION");

        return new QuerySqlAction(query);
    }

    @Override
    protected UniqueConstraint convertToObject(Object object, ObjectReference relatedTo, SnapshotObjectsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        String relatedToUniqueConstraintName = null;
        if (relatedTo.instanceOf(UniqueConstraint.class)) {
            relatedToUniqueConstraintName = relatedTo.name;
        }

        String constraintName = row.get("CONSTRAINT_NAME", String.class);
        String tableCat = row.get("TABLE_CATALOG", String.class);
        String tableSchema = row.get("TABLE_SCHEMA", String.class);
        String tableName = row.get("TABLE_NAME", String.class);

        String columnName = row.get("COLUMN_NAME", String.class);
        Boolean isDeferrable = row.get("IS_DEFERRABLE", Boolean.class);
        Boolean initiallyDeferred = row.get("INITIALLY_DEFERRED", Boolean.class);

        if (relatedToUniqueConstraintName != null && constraintName != null && !constraintName.equals(relatedToUniqueConstraintName)) {
            return null;
        }

        UniqueConstraint.UniqueConstraintReference objectReference;
        if (tableCat != null && tableSchema == null) {
            objectReference = new UniqueConstraint.UniqueConstraintReference(new ObjectReference(Table.class, tableCat, tableName), constraintName);
        } else {
            objectReference = new UniqueConstraint.UniqueConstraintReference(new ObjectReference(Table.class, tableCat, tableSchema, tableName), constraintName);
        }

        UniqueConstraint fk = new UniqueConstraint(objectReference);
        fk.columns.add(columnName);
        fk.deferrable = isDeferrable;
        fk.initiallyDeferred = initiallyDeferred;

        return fk;
    }

    @Override
    protected DelegateResult.Modifier createModifier(ObjectReference relatedTo, final SnapshotObjectsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<UniqueConstraint> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(UniqueConstraint.class);
                Map<ObjectReference, UniqueConstraint> combinedResults = new HashMap<>();
                for (UniqueConstraint uniqueConstraint : rawResults) {
                    UniqueConstraint existingUq = combinedResults.get(uniqueConstraint.toReference());
                    if (existingUq == null) {
                        combinedResults.put(uniqueConstraint.toReference(), uniqueConstraint);
                    } else {
                        existingUq.columns.addAll(uniqueConstraint.columns);
                    }
                }

                return new ObjectBasedQueryResult(originalAction, new ArrayList(combinedResults.values()));
            }
        };
    }
}

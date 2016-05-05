package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.util.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                UniqueConstraint.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
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

        SelectDataAction query = new SelectDataAction("TC", new RelationReference<View>(View.class, "INFORMATION_SCHEMA", "TABLE_CONSTRAINTS"),
                new SelectDataAction.SelectedColumn("TC", "CONSTRAINT_NAME", null),
                (database.supports(Catalog.class, scope) ? new SelectDataAction.SelectedColumn("TC", "TABLE_CATALOG", null) : new SelectDataAction.SelectedColumn(null, "NULL", "TABLE_CATALOG", true)),
                new SelectDataAction.SelectedColumn("TC", "TABLE_SCHEMA", null),
                new SelectDataAction.SelectedColumn("TC", "TABLE_NAME", null),
                (database.supports(Database.Feature.DEFERRABLE_CONSTRAINTS, scope) ? new SelectDataAction.SelectedColumn("TC", "IS_DEFERRABLE", null) : new SelectDataAction.SelectedColumn(null, "NULL", "IS_DEFERRABLE", true)),
                (database.supports(Database.Feature.DEFERRABLE_CONSTRAINTS, scope) ? new SelectDataAction.SelectedColumn("TC", "INITIALLY_DEFERRED", null) : new SelectDataAction.SelectedColumn(null, "NULL", "INITIALLY_DEFERRED", true)),
                new SelectDataAction.SelectedColumn("KCU", "COLUMN_NAME", null)
        )
                .addJoin(new SelectDataAction.JoinedRelation(new RelationReference(View.class, "INFORMATION_SCHEMA", "KEY_COLUMN_USAGE"), "KCU", SelectDataAction.JoinType.inner)
                                .addOnClause("TC.CONSTRAINT_NAME=KCU.CONSTRAINT_NAME")
                                .addOnClause(database.supports(Catalog.class, scope) ? "TC.TABLE_CATALOG=KCU.TABLE_CATALOG" : "")
                                .addOnClause("TC.TABLE_SCHEMA=KCU.TABLE_SCHEMA")
                                .addOnClause("TC.TABLE_NAME=KCU.TABLE_NAME")
                );

        if (catalogName != null) {
            query.addWhere("TC.CONSTRAINT_CATALOG=" + database.quoteString(catalogName, scope));
        }
        if (schemaName != null) {
            query.addWhere("TC.CONSTRAINT_SCHEMA=" + database.quoteString(schemaName, scope));
        }
        if (tableName != null) {
            query.addWhere("TC.TABLE_NAME=" + database.quoteString(tableName, scope));
        }
        if (constraintName != null) {
            query.addWhere("TC.CONSTRAINT_NAME=" + database.quoteString(constraintName, scope));
        }

        query.addOrder(new SelectDataAction.OrderedByColumn("ORDINAL_POSITION"));

        return query;
    }

    @Override
    protected UniqueConstraint convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
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

        UniqueConstraintReference uqRef;
        if (tableCat != null && tableSchema == null) {
            uqRef = new UniqueConstraintReference(constraintName, new RelationReference(Table.class, tableCat, tableName));
        } else {
            uqRef = new UniqueConstraintReference(constraintName, new RelationReference(Table.class, tableCat, tableSchema, tableName));
        }

        UniqueConstraint fk = new UniqueConstraint(uqRef.name, uqRef.container);
        fk.columns.add(columnName);
        fk.deferrable = isDeferrable;
        fk.initiallyDeferred = initiallyDeferred;

        return fk;
    }

    @Override
    protected DelegateResult.Modifier createModifier(DatabaseObjectReference relatedTo, final SnapshotItemsAction originalAction, Scope scope) {
        return new RowsToObjectsModifier(relatedTo, originalAction, scope) {
            @Override
            public ActionResult rewrite(ActionResult result) throws ActionPerformException {
                List<UniqueConstraint> rawResults = ((ObjectBasedQueryResult) super.rewrite(result)).asList(UniqueConstraint.class);
                Map<UniqueConstraintReference, UniqueConstraint> combinedResults = new HashMap<>();
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

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateIndexesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateIndexesLogic extends AbstractActionLogic<CreateIndexesAction> {

    public enum Clauses {
        indexName,
        columns,
        tableName,
        tablespace,
    }

    @Override
    protected Class<CreateIndexesAction> getSupportedAction() {
        return CreateIndexesAction.class;
    }

    @Override
    public ValidationErrors validate(CreateIndexesAction action, final Scope scope) {
        final ValidationErrors validationErrors = super.validate(action, scope)
                .checkRequiredFields("indexes",
                        "indexes.columns", "indexes.columns.name",
                        "indexes.relation", "indexes.relation.name");

        final Database database = scope.getDatabase();

        if (!database.supports(Database.Feature.INDEXES_CLUSTERED, scope)) {
            validationErrors.checkUnsupportedFields("indexes.clustered");
        }

        //Most databases do not support these. Don't support by default
        validationErrors.checkUnsupportedFields("indexes.tablespace");

        if (!database.supports(Database.Feature.INDEXES_IN_SEPARATE_SCHEMA, scope)) {
            validationErrors.checkUnsupportedFields("indexes.indexSchema");
        }
        validationErrors.checkUnsupportedFields("indexes.tablespace");

        validationErrors.checkField("indexes.columns", new ValidationErrors.FieldCheck<Index.IndexedColumn>() {
            @Override
            public String check(Index.IndexedColumn column) {
                if (column == null || column.direction == null) {
                    return null;
                }
                switch (column.direction) {
                    case ASC:
                        return database.supports(Database.Feature.INDEXES_ASC, scope) ? null : "Cannot specify " + column.direction + " indexes";
                    case DESC:
                        return database.supports(Database.Feature.INDEXES_DESC, scope) ? null : "Cannot specify " + column.direction + " indexes";
                }
                return "Cannot specify " + column.direction + " indexes";

            }
        });

        return validationErrors;
    }

    @Override
    public ActionStatus checkStatus(CreateIndexesAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (Index actionIndex : action.indexes) {
                RelationReference table = actionIndex.relation;
                if (table.type == null || table.type.equals(Item.class)) {
                    table.type = Table.class;
                }

                Index snapshotIndex = scope.getSingleton(SnapshotFactory.class).snapshot(Index.class, actionIndex.toReference(), scope);
                if (snapshotIndex == null) {
                    result.assertApplied(false, "Index '" + actionIndex.toReference() + "' not found");
                } else {
                    List<String> ignoreList = new ArrayList<>();
                    ignoreList.add("indexSchema");

                    if (actionIndex.clustered == null || !actionIndex.clustered) {
                        ignoreList.add("clustered");
                    }
                    if (actionIndex.unique == null) {
                        ignoreList.add("unique");
                    }

                    result.assertCorrect(actionIndex, snapshotIndex, ignoreList);

                    if (actionIndex.columns.size() == snapshotIndex.columns.size()) {
                        for (int i = 0; i < actionIndex.columns.size(); i++) {
                            result.assertCorrect(actionIndex.columns.get(i), snapshotIndex.columns.get(i), Arrays.asList("direction"));

                            if (actionIndex.columns.get(i).direction != null) {
                                result.assertCorrect(actionIndex.columns.get(i).direction.equals(snapshotIndex.columns.get(i).direction), "column " + actionIndex.columns.get(i).name + " direction is incorrect. Expected " + actionIndex.columns.get(i).direction + ", got " + snapshotIndex.columns.get(i).direction);
                            }
                        }
                    } else {
                        result.assertCorrect(false, "columns sizes are different");
                    }
                    if (actionIndex.indexSchema != null) {
                        result.assertPropertyCorrect(actionIndex, snapshotIndex, "indexSchema");
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            return result.unknown(e);
        }
    }

    @Override
    public ActionResult execute(CreateIndexesAction action, Scope scope) throws ActionPerformException {

        List<Action> actions = new ArrayList<>();

        for (Index index : action.indexes) {
            actions.addAll(Arrays.asList(execute(index, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action execute(Index index, CreateIndexesAction action, Scope scope) {
        return new ExecuteSqlAction(generateSql(index, action, scope).toString());
    }

    protected StringClauses generateSql(Index index, CreateIndexesAction action, final Scope scope) {
        final Database database = scope.getDatabase();
        IndexReference indexName = index.toReference();
        RelationReference tableName = index.relation;
        String tablespace = index.tablespace;


        StringClauses clauses = new StringClauses().append("CREATE");

        if (ObjectUtil.defaultIfNull(index.unique, false)) {
            clauses.append("UNIQUE ");
        }

        clauses.append("INDEX ");

        if (indexName != null) {
            clauses.append(Clauses.indexName, database.quoteObjectName(indexName.name, Index.class, scope));
        }

        clauses.append("ON");

        clauses.append(Clauses.tableName, database.quoteObjectName(tableName, scope));

        clauses.append(Clauses.columns, "(" + StringUtil.join(index.columns, ", ", new StringUtil.StringUtilFormatter<Index.IndexedColumn>() {
            @Override
            public String toString(Index.IndexedColumn column) {
                Boolean computed = column.computed;
                String name;
                if (computed == null) {
                    name = database.quoteObjectName(column.name, Column.class, scope);
                } else if (computed) {
                    name = column.name;
                } else {
                    name = database.quoteObjectName(column.name, Column.class, scope);
                }

                if (column.direction != null) {
                    name += " " + column.direction;
                }
                return name;
            }
        }) + ")");


        if (tablespace != null && database.supports(Database.Feature.TABLESPACES, scope)) {
            clauses.append("TABLESPACE " + tablespace);
        }

        return clauses;
    }
}

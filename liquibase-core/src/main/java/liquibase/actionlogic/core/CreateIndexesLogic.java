package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateIndexesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.ValidationErrors;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import liquibase.util.StringUtils;

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
                .checkRequiredFields("indexes.columns", "indexes.columns.name",
                        "indexes.table");

        final Database database = scope.getDatabase();

        if (!database.supports(Database.Feature.CLUSTERED_INDEXES, scope)) {
            validationErrors.checkUnsupportedFields("indexes.clustered");
        }

        validationErrors.checkField("indexes.column", new ValidationErrors.FieldCheck<Index.IndexedColumn>() {
            @Override
            public String check(Index.IndexedColumn column) {
                if (column == null || column.direction == null) {
                    return null;
                }
                switch (column.direction) {
                    case ASC:
                        return database.supports(Database.Feature.INDEXES_ASC, scope) ? null : "Cannot specify " + column.direction + " primary keys";
                    case DESC:
                        return database.supports(Database.Feature.INDEXES_DESC, scope) ? null : "Cannot specify " + column.direction + " primary keys";
                }
                return "Cannot specify " + column.direction + " primary keys";

            }
        });

        return validationErrors;
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
        ObjectReference indexName = index.toReference();
        ObjectReference tableName = index.table;
        String tablespace = index.tablespace;


        StringClauses clauses = new StringClauses().append("CREATE");

        if (ObjectUtil.defaultIfEmpty(index.unique, false)) {
            clauses.append("UNIQUE ");
        }

        clauses.append("INDEX ");

        if (indexName != null) {
            clauses.append(Clauses.indexName, database.quoteObjectName(indexName.name, Index.class, scope));
        }

        clauses.append("ON");

        clauses.append(Clauses.tableName, database.quoteObjectName(tableName, scope));

        clauses.append(Clauses.columns, "(" + StringUtils.join(index.columns, ", ", new StringUtils.StringUtilsFormatter<Index.IndexedColumn>() {
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

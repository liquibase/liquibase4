package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.DropIndexesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Index;
import liquibase.item.core.IndexReference;
import liquibase.item.core.SchemaReference;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.List;

public class DropIndexesLogic extends AbstractActionLogic<DropIndexesAction> {

    public enum Clauses {
        indexName
    }

    @Override
    protected Class<DropIndexesAction> getSupportedAction() {
        return DropIndexesAction.class;
    }

    @Override
    public ValidationErrors validate(DropIndexesAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("indexes", "indexes.name");

        if (!scope.getDatabase().supports(Database.Feature.INDEXES_IN_SEPARATE_SCHEMA, scope)) {
            errors.checkUnsupportedFields("indexes.indexSchema");
        }

        return errors;
    }


    @Override
    public ActionResult execute(DropIndexesAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (IndexReference index : action.indexes) {
            actions.add(new ExecuteSqlAction(generateSql(index, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected StringClauses generateSql(IndexReference index, DropIndexesAction action, Scope scope) {
        Database database = scope.getDatabase();

        String indexName = database.quoteObjectName(index.name, Index.class, scope);
        SchemaReference schemaReference;
        if (database.supports(Database.Feature.INDEXES_IN_SEPARATE_SCHEMA, scope)) {
            schemaReference = index.indexSchema;
        } else {
            schemaReference = index.getSchema();
        }
        if (schemaReference != null) {
            indexName = database.quoteObjectName(schemaReference, scope) + "." + indexName;
        }

        return new StringClauses()
                .append("DROP")
                .append("INDEX")
                .append(Clauses.indexName, indexName);
    }

    @Override
    public ActionStatus checkStatus(DropIndexesAction action, Scope scope) {
        ActionStatus status = new ActionStatus();
        try {
            for (IndexReference index : action.indexes) {
                return status.assertCorrect(scope.getSingleton(SnapshotFactory.class).snapshot(index, scope) == null, "Index " + index + " was not dropped");
            }
        } catch (ActionPerformException e) {
            return status.unknown(e);
        }

        return status;

    }
}

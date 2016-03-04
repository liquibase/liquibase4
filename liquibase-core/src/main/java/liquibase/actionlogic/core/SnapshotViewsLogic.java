package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SelectDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.exception.ActionPerformException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;
import liquibase.util.SqlParser;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;
import liquibase.util.Validate;

import java.util.List;

public class SnapshotViewsLogic extends AbstractSnapshotDatabaseObjectsLogic<View> {

    @Override
    protected Class<View> getTypeToSnapshot() {
        return View.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                View.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        RelationReference viewRef;
        if (relatedTo instanceof CatalogReference) {
            if (!scope.getDatabase().supports(Catalog.class, scope)) {
                throw new ActionPerformException("Cannot snapshot catalogs on " + scope.getDatabase().getShortName());
            }
            viewRef = new RelationReference(View.class, null, new SchemaReference(null, (CatalogReference) relatedTo));
        } else if (relatedTo instanceof SchemaReference) {
            viewRef = new RelationReference(View.class, null, (SchemaReference) relatedTo);
        } else if (relatedTo instanceof RelationReference) {
            viewRef = (RelationReference) relatedTo;
        } else {
            throw Validate.failure("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

        List<String> nameParts = viewRef.asList(3);

        if (scope.getDatabase().supports(Catalog.class, scope)) {
            return createSnapshotAction(nameParts.get(0), nameParts.get(1), nameParts.get(2), scope);
        } else {
            return createSnapshotAction(null, nameParts.get(1), nameParts.get(2), scope);
        }
    }

    protected Action createSnapshotAction(String catalogName, String schemaName, String viewName, Scope scope) {
        SelectDataAction action = new SelectDataAction(new RelationReference(Table.class, "INFORMATION_SCHEMA", "VIEWS"),
                new SelectDataAction.SelectedColumn("TABLE_NAME"),
                new SelectDataAction.SelectedColumn("VIEW_DEFINITION")
        );
        if (scope.getDatabase().supports(Catalog.class, scope)) {
            action.addColumn(new SelectDataAction.SelectedColumn("TABLE_CATALOG"));
        }
        if (scope.getDatabase().supports(Schema.class, scope)) {
            action.addColumn(new SelectDataAction.SelectedColumn("TABLE_SCHEMA"));
        }


        if (viewName != null) {
            action.addWhere("TABLE_NAME=" + scope.getDatabase().quoteString(viewName, scope));
        }
        if (schemaName != null) {
            action.addWhere("TABLE_SCHEMA=" + scope.getDatabase().quoteString(schemaName, scope));
        }
        if (catalogName != null) {
            action.addWhere("TABLE_CATALOG=" + scope.getDatabase().quoteString(catalogName, scope));
        }

        return action;
    }

    @Override
    protected View convertToObject(Object object, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) object;

        View view = new View();
        view.name = row.get("TABLE_NAME", String.class);

        String catalogName = row.get("TABLE_CATALOG", String.class);
        String schemaName = row.get("TABLE_SCHEMA", String.class);

        if (schemaName != null || catalogName != null) {
            view.schema = new SchemaReference(catalogName, schemaName);
        }

        String remarks = StringUtil.trimToNull(row.get("REMARKS", String.class));
        if (remarks != null) {
            remarks = remarks.replace("''", "'"); //come back escaped sometimes
        }
        view.remarks = remarks;


        view.definition = parseViewDefinition(row.get("VIEW_DEFINITION", String.class));

        return view;
    }

    protected StringClauses parseViewDefinition(String originalViewDefinition) {
        StringClauses clauses = SqlParser.parse(originalViewDefinition.trim(), true, true);
        StringClauses.ClauseIterator clauseIterator = clauses.getClauseIterator();

        while (clauseIterator.hasNext()) {
            Object next = clauseIterator.next();
            if (next instanceof String && ((String) next).equalsIgnoreCase("select")) {
                break;
            }
            clauseIterator.remove();
        }
        return clauses;
    }


}

package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogicOffline;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;

public class SnapshotTablesLogicOffline extends AbstractSnapshotDatabaseObjectsLogicOffline<Table> {

    @Override
    protected Class<Table> getTypeToSnapshot() {
        return Table.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Schema.class,
                Catalog.class,
                Table.class,
                AbstractRelationBasedObject.class,
        };
    }

    @Override
    public boolean executeInteractsExternally(SnapshotItemsAction action, Scope scope) {
        return true;
    }

    @Override
    protected boolean isRelatedTo(Table table, DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) {
        if (relatedTo == null) {
            return true;
        } else if (relatedTo.instanceOf(Relation.class) && relatedTo.type.isAssignableFrom(Table.class)) {
            if (table.name == null) { //get all tables in the schema
                return isRelatedTo(table, table.schema, action, scope);
            }
            return table.toReference().equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Schema.class)) {
            return table.schema.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Catalog.class)) {
            SchemaReference schemaName = table.schema;

            return schemaName.container != null && schemaName.container.equals(relatedTo, true);
        } else if (relatedTo instanceof AbstractRelationBasedObject.RelationBasedObjectReference) {
            return isRelatedTo(table, ((AbstractRelationBasedObject.RelationBasedObjectReference) relatedTo).getRelation(), action, scope);
        } else {
            throw new UnexpectedLiquibaseException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }

    }
}

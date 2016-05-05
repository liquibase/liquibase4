package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogicOffline;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;

public class SnapshotPrimaryKeysLogicOffline extends AbstractSnapshotDatabaseObjectsLogicOffline<PrimaryKey> {

    @Override
    protected Class<PrimaryKey> getTypeToSnapshot() {
        return PrimaryKey.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                PrimaryKey.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    public boolean executeInteractsExternally(SnapshotItemsAction action, Scope scope) {
        return true;
    }

    @Override
    protected boolean isRelatedTo(PrimaryKey primaryKey, DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) {
        if (relatedTo.instanceOf(PrimaryKey.class)) {
            return primaryKey.toReference().equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Relation.class)) {
            RelationReference tableName = primaryKey.relation;
            return tableName != null && tableName.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Schema.class)) {
            SchemaReference schema = primaryKey.getSchema();
            return schema != null && schema.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Catalog.class)) {
            RelationReference tableName = primaryKey.relation;
            if (tableName == null || tableName.container == null) {
                return false;
            }
            SchemaReference schemaName = tableName.getSchema();

            return schemaName.container != null && schemaName.container.equals(relatedTo.container, true);
        } else {
            throw new UnexpectedLiquibaseException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }
    }
}
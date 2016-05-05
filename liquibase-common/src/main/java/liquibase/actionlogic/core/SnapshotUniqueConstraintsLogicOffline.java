package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogicOffline;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.core.*;

public class SnapshotUniqueConstraintsLogicOffline extends AbstractSnapshotDatabaseObjectsLogicOffline<UniqueConstraint> {

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
    public boolean executeInteractsExternally(SnapshotItemsAction action, Scope scope) {
        return true;
    }

    @Override
    protected boolean isRelatedTo(UniqueConstraint uniqueConstraint, DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) {
        if (relatedTo.instanceOf(UniqueConstraint.class)) {
            return uniqueConstraint.toReference().equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Relation.class)) {
            RelationReference tableName = uniqueConstraint.relation;
            return tableName != null && tableName.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Schema.class)) {
            SchemaReference schema = uniqueConstraint.getSchema();
            return schema != null && schema.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Catalog.class)) {
            CatalogReference catalog = uniqueConstraint.getCatalog();
            return catalog != null && catalog.equals(relatedTo.container, true);
        } else {
            throw new UnexpectedLiquibaseException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }
    }
}
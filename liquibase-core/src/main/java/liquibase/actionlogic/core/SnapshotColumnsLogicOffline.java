package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogicOffline;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;

public class SnapshotColumnsLogicOffline extends AbstractSnapshotDatabaseObjectsLogicOffline<Column> {

    @Override
    protected Class<Column> getTypeToSnapshot() {
        return Column.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Column.class,
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
    protected boolean isRelatedTo(Column column, DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) {
        if (relatedTo.instanceOf(Column.class)) {
            return column.toReference().equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Relation.class)) {
            RelationReference table = column.relation;
            return table != null && table.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Schema.class)) {
            SchemaReference schema = column.getSchema();
            return schema != null && schema.equals(relatedTo, true);
        } else if (relatedTo.instanceOf(Catalog.class)) {
            RelationReference table = column.relation;
            if (table == null || table.container == null) {
                return false;
            }
            SchemaReference schema = table.getSchema();

            return schema.container != null && schema.container.equals(relatedTo, true);
        } else {
            throw new UnexpectedLiquibaseException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }
    }
}
package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogicOffline;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;

public class SnapshotColumnsLogicOffline extends AbstractSnapshotDatabaseObjectsLogicOffline<Column> {

    @Override
    protected Class<Column> getTypeToSnapshot() {
        return Column.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                Column.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    public boolean executeInteractsExternally(SnapshotObjectsAction action, Scope scope) {
        return true;
    }


    @Override
    protected boolean isRelatedTo(Column column, ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) {
        if (relatedTo.instanceOf(Column.class)) {
            return column.getName().equals(relatedTo.name);
        } else if (relatedTo.instanceOf(Relation.class)) {
            ObjectReference tableName = column.table;
            return tableName != null && tableName.equals(relatedTo);
        } else if (relatedTo.instanceOf(Schema.class)) {
            ObjectReference tableName = column.table;
            return tableName != null && tableName.container != null && tableName.container.equals((relatedTo.name));
        } else if (relatedTo.instanceOf(Catalog.class)) {
            ObjectReference tableName = column.table;
            if (tableName == null || tableName.container == null) {
                return false;
            }
            ObjectReference schemaName = tableName.container;

            return schemaName.container != null && schemaName.container.equals((relatedTo.name));
        } else {
            throw new UnexpectedLiquibaseException("Unexpected relatedTo type: " + relatedTo.getClass().getName());
        }
    }
}
package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.SnapshotObjectsAction;
import liquibase.actionlogic.ActionLogic;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.UniqueConstraint;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.util.CollectionUtil;

public class SnapshotUniqueConstraintsLogicOffline extends AbstractSnapshotObjectsLogicOffline implements ActionLogic.InteractsExternally {

    @Override
    protected Class<? extends LiquibaseObject> getTypeToSnapshot() {
        return UniqueConstraint.class;
    }

    @Override
    protected Class<? extends LiquibaseObject>[] getSupportedRelatedTypes() {
        return new Class[]{
                UniqueConstraint.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    public boolean interactsExternally(Action action, Scope scope) {
        return true;
    }

    protected CollectionUtil.CollectionFilter<? extends LiquibaseObject> getDatabaseObjectFilter(final ObjectReference relatedTo, SnapshotObjectsAction action, Scope scope) {
        return new CollectionUtil.CollectionFilter<UniqueConstraint>() {
            @Override
            public boolean include(UniqueConstraint uniqueConstraint) {
                if (relatedTo.instanceOf(UniqueConstraint.class)) {
                    return uniqueConstraint.getName().equals(relatedTo.name);
                } else if (relatedTo.instanceOf(Relation.class)) {
                    ObjectReference tableName = uniqueConstraint.table;
                    return tableName != null && tableName.equals((relatedTo));
                } else if (relatedTo.instanceOf(Schema.class)) {
                    ObjectReference tableName = uniqueConstraint.table;
                    return tableName != null && tableName.container != null && tableName.container.equals((relatedTo.container));
                } else if (relatedTo.instanceOf(Catalog.class)) {
                    ObjectReference tableName = uniqueConstraint.table;
                    if (tableName == null || tableName.container == null) {
                        return false;
                    }
                    ObjectReference schemaName = tableName.container;

                    return schemaName.container != null && schemaName.container.equals((relatedTo.container));
                } else {
                    throw new UnexpectedLiquibaseException("Unexpected relatedTo type: "+relatedTo.getClass().getName());
                }
            }
        };

    }
}
package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

public class PrimaryKeyReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public PrimaryKeyReference() {
        super(PrimaryKey.class);
    }

    public PrimaryKeyReference(String... names) {
        super(PrimaryKey.class, names);
    }

    public PrimaryKeyReference(String pkName, RelationReference table) {
        super(PrimaryKey.class, pkName, table);
    }
}

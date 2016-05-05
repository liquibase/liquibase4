package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

public class ForeignKeyReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public ForeignKeyReference() {
        super(ForeignKey.class);
    }

    public ForeignKeyReference(String fkName, RelationReference baseTable) {
        super(ForeignKey.class, fkName, baseTable);
    }

    public ForeignKeyReference(String... names) {
        super(ForeignKey.class, names);
    }

 }

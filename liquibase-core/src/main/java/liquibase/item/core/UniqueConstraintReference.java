package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

import java.util.List;

public class UniqueConstraintReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public List<String> columns;

    public UniqueConstraintReference() {
        super(UniqueConstraint.class);
    }

    public UniqueConstraintReference(String... name) {
        super(UniqueConstraint.class, name);
    }

    public UniqueConstraintReference(String constraintName, RelationReference relation) {
        super(UniqueConstraint.class, constraintName, relation);
    }
}

package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

public class CheckConstraintReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public CheckConstraintReference() {
        super(CheckConstraint.class);
    }

    public CheckConstraintReference(String... name) {
        super(CheckConstraint.class, name);
    }

    public CheckConstraintReference(String constraintName, RelationReference relation) {
        super(CheckConstraint.class, constraintName, relation);
    }
}

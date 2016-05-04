package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;

/**
 * Describes a check constraint on a table.
 */
public class CheckConstraint extends AbstractRelationBasedObject<CheckConstraintReference> {

    public Boolean disabled;
    public String body;

    @Override
    public CheckConstraintReference toReference() {
        return new CheckConstraintReference(name, relation);
    }
}
